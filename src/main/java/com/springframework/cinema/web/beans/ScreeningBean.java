package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.movie.MovieService;
import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomSeat;
import com.springframework.cinema.domain.room.RoomSeatService;
import com.springframework.cinema.domain.room.RoomService;
import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningSeat;
import com.springframework.cinema.domain.screening.ScreeningSeatService;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.domain.user.CustomUserDetails;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.DateUtil;
import com.springframework.cinema.infrastructure.util.SecurityUtil;

@Component("screeningBean")
@Scope("session")
public class ScreeningBean implements Serializable {
	private static final long serialVersionUID = -3210711807003424547L;
	
	private Collection<Screening> screenings;
	private Collection<Screening> filteredScreenings;
	
	private Screening screening;
	private String screeningDimension;
	private String translation;
	private ArrayList<Movie> movies;
	private ArrayList<Room> rooms;
	private String mode;
	
	private Screening selectedScreening;
	private Collection<ScreeningSeat> selectedScreeningSeats;
	private Ticket ticket;
	
	@Inject
	private UserService userService;
	
	@Inject
	private ScreeningService screeningService;
	
	@Inject
	private MovieService movieService;
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private RoomSeatService roomSeatService;
	
	@Inject
	private ScreeningSeatService screeningSeatService;
	
	@Inject
	private TicketService ticketService;

	public ScreeningBean(){}
	
	@PostConstruct
	public void init(){
		screenings = screeningService.findAll();
		filteredScreenings = new ArrayList<Screening>();
		
		movies = (ArrayList<Movie>) movieService.findAvailable();
		rooms = (ArrayList<Room>) roomService.findAll();
		resetScreening();
		
		ticket = new Ticket();
		ticket.setScreeningSeats(new ArrayList<ScreeningSeat>());
	}

	public boolean filterByDate(Object value, Object filter, Locale locale) {
	        if( filter == null ) { return false; }
	        if( value == null ) { return false; }
	        return ((Date)value).compareTo((Date)filter) == 0;
	    }
	
	public void resetScreening(){
		screening = new Screening();
		screening.setMovie(movies.isEmpty() ? new Movie() : movies.get(0));
		screening.setRoom(rooms.isEmpty() ? new Room() : rooms.get(0));
		screeningDimension = screening.getMovie().getTwoDimensional() ? "2D" : "3D";
		translation = "";
		if(!movies.isEmpty())
			updateFinishesAt();
	}
	
	public void resetMovieProperties(){
		screeningDimension = screening.getMovie().getTwoDimensional() ? "2D" : "3D";
		translation = "";
		updateFinishesAt();
	}
	
	public void updateFinishesAt(){
		Integer movieDuration = screening.getMovie().getDuration();
		screening.setStartsAt(DateUtil.setDateTime(screening.getDate(), screening.getStartsAt()));
		LocalDateTime startsAt = LocalDateTime.ofInstant(screening.getStartsAt().toInstant(), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(1)));
		LocalDateTime finishesAt = startsAt.plusHours(movieDuration/60).plusMinutes(movieDuration%60);
		Date finishesAtAsDate = Date.from(finishesAt.toInstant(ZoneOffset.ofHours(1)));
		screening.setFinishesAt(finishesAtAsDate);
	}
	
	public void initEditDialog(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long screeningId = Long.parseLong((params.get("screening_id")));
		screening = screeningService.findById(screeningId);
	}
	
	public void createScreening() throws IOException{
		//TODO startsDate gets reseted to 1970 along the way i don't know how to fix this issue (temporary fix)
		screening.setStartsAt(DateUtil.setDateTime(screening.getDate(), screening.getStartsAt()));
		screening.setMovieAttributes(screeningDimension + " " + translation);
		if(screening.getId() != null && mode.equals("Update")){
			Screening screeningDbRecord = screeningService.findById(screening.getId());
			if(!screeningDbRecord.getRoom().equals(screening.getRoom()))
			{
				Collection<ScreeningSeat> screeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(screening.getId()); 
				for( ScreeningSeat seat : screeningSeats)
					ticketService.deleteByScreeningSeatsId(seat.getId());
				screeningSeatService.deleteByScreeningId(screening.getId());
			}
		}
		screeningService.save(screening);
		Collection<RoomSeat> roomSeats = roomSeatService.findRoomSeatsByRoomId(screening.getRoom().getId());
		screeningSeatService.saveRoomSeatsAsScreeningSeats(roomSeats, screening);
		init();
		RequestContext.getCurrentInstance().execute("PF('addUpdateScreeningDialog').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Screening " + mode.toLowerCase() + "d successfully."));
	}
	
	public void deleteScreening() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long screeningId = Long.parseLong((params.get("screening_id")));
		Collection<ScreeningSeat> screeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(screeningId); 
		for( ScreeningSeat seat : screeningSeats)
			ticketService.deleteByScreeningSeatsId(seat.getId());
		screeningSeatService.deleteByScreeningId(screeningId);
		screeningService.delete(screeningId);
		init();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Screening deleted successfully."));
	}
	
	public void redirectIfScreeningDataEmpty() throws IOException{
		if(selectedScreening == null || selectedScreeningSeats == null)
			FacesContext.getCurrentInstance().getExternalContext().redirect("/");
	}
	
	public void initBuyTicketDialog() throws IOException {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		selectedScreening = screeningService.findById(Long.parseLong((params.get("screening_id"))));
		selectedScreeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(selectedScreening.getId());
		ticket = new Ticket();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("/ticket/book");
	}
	
	public void selectDeselectSeat() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long selectedSeatId = Long.parseLong((params.get("seat_id")));
		ScreeningSeat selectedSeat = null;
		for(ScreeningSeat seat : selectedScreeningSeats){
			if(seat.getId().equals(selectedSeatId))
				selectedSeat = seat;
		}
		
		boolean seatAlreadyChoosen = isSeatOnTicket(selectedSeat); 
		
		if(selectedSeat != null && !seatAlreadyChoosen)
			ticket.getScreeningSeats().add(selectedSeat);
		else if(seatAlreadyChoosen)
			ticket.getScreeningSeats().remove(selectedSeat);
	}
	
	public boolean isSeatOnTicket(ScreeningSeat seat){
		if(ticket.getScreeningSeats().contains(seat))
			return true;
		return false;
	}
	
	public void bookTicket() throws IOException{
		String message = new String();
		boolean success = true;
		if(SecurityUtil.isAuthenticated()){
			selectedScreeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(selectedScreening.getId());
			Collection<ScreeningSeat> ticketScreeningSeats = ticket.getScreeningSeats();
			for(ScreeningSeat seat : selectedScreeningSeats){
				if(ticketScreeningSeats.contains(seat)){
					if(!seat.getAvailability()){
						ticketScreeningSeats.remove(seat);
						message += " " + seat.getLabel();
						success = false;
					}
				}
			}
			
			if(success){
				ticket.setUser(userService.findByUsername(((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
				for(ScreeningSeat seat : selectedScreeningSeats){
					if(ticketScreeningSeats.contains(seat)){
						seat.setAvailability(false);
						screeningSeatService.save(seat);
					}
				}
				ticketService.save(ticket);
				FacesContext.getCurrentInstance().getExternalContext().redirect("/");
				ticket = new Ticket();
			} else {
				FacesContext.getCurrentInstance().addMessage("ticketMsg", new FacesMessage(FacesMessage.SEVERITY_ERROR,"There was an error during booking a ticket process.",
						"Someone already took some of the seats you choosed: " + message + "."));
			}
		} else {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        FacesContext.getCurrentInstance().addMessage("logInMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","You are not logged in. Please log in and book your ticket again."));
			externalContext.getFlash().setKeepMessages(true);
			externalContext.redirect("/login");
		}
			
	}
	
	public Collection<Screening> getScreenings() { return screenings; }
	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }
	
	public Collection<Screening> getFilteredScreenings() { return filteredScreenings; }
	public void setFilteredScreenings(Collection<Screening> filteredScreenings) { this.filteredScreenings = filteredScreenings; }

	public Screening getScreening() { return screening; }
	public void setScreening(Screening screening) { this.screening = screening; }

	public String getScreeningDimension() {
		return screeningDimension;
	}

	public void setScreeningDimension(String screeningDimension) {
		this.screeningDimension = screeningDimension;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public Collection<Movie> getMovies() { return movies; }
	public void setMovies(ArrayList<Movie> movies) { this.movies = movies; }
	
	public Collection<Room> getRooms() { return rooms; }
	public void setRooms(ArrayList<Room> rooms) { this.rooms = rooms; }

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Collection<ScreeningSeat> getSelectedScreeningSeats() {
		return selectedScreeningSeats;
	}

	public void setSelectedScreeningSeats(Collection<ScreeningSeat> selectedScreeningSeats) {
		this.selectedScreeningSeats = selectedScreeningSeats;
	}

	public Screening getSelectedScreening() {
		return selectedScreening;
	}

	public void setSelectedScreening(Screening selectedScreening) {
		this.selectedScreening = selectedScreening;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
}
