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

import org.springframework.context.annotation.Scope;
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
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.infrastructure.util.DateUtil;

@Component("screeningBean")
@Scope("session")
public class ScreeningBean implements Serializable {
	//TODO manageScreenings sends wrong screening to delete (implement selectable)
	private static final long serialVersionUID = -3210711807003424547L;
	
	private Collection<Screening> screenings;
	private Collection<Screening> filteredScreenings;
	
	private Screening screening;
	private ArrayList<Movie> movies;
	private ArrayList<Room> rooms;
	
	private Long screeningId;
	
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
	
	public Long getScreeningId() {
		return screeningId;
	}

	public void setScreeningId(Long screeningId) {
		this.screeningId = screeningId;
	}

	public ScreeningBean(){}
	
	@PostConstruct
	public void init(){
		screenings = screeningService.findAll();
		filteredScreenings = new ArrayList<Screening>();
		
		movies = (ArrayList<Movie>) movieService.findAvailable();
		rooms = (ArrayList<Room>) roomService.findAll();
		screening = new Screening();
		screening.setMovie(movies.isEmpty() ? new Movie() : movies.get(0));
		screening.setRoom(rooms.isEmpty() ? new Room() : rooms.get(0));
		
		if(!movies.isEmpty())
			updateFinishesAt();
	}

	public Collection<Screening> getScreenings() { return screenings; }
	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }
	
	public Collection<Screening> getFilteredScreenings() { return filteredScreenings; }
	public void setFilteredScreenings(Collection<Screening> filteredScreenings) { this.filteredScreenings = filteredScreenings; }

	public Screening getScreening() { return screening; }
	public void setScreening(Screening screening) { this.screening = screening; }

	public Collection<Movie> getMovies() { return movies; }
	public void setMovies(ArrayList<Movie> movies) { this.movies = movies; }
	
	public Collection<Room> getRooms() { return rooms; }
	public void setRooms(ArrayList<Room> rooms) { this.rooms = rooms; }

	public boolean filterByDate(Object value, Object filter, Locale locale) {
	        if( filter == null ) { return false; }
	        if( value == null ) { return false; }
	        return ((Date)value).compareTo((Date)filter) == 0;
	    }
	
	public void updateFinishesAt(){
		Integer movieDuration = screening.getMovie().getDuration();
		screening.setStartsAt(DateUtil.setDateTime(screening.getDate(), screening.getStartsAt()));
		LocalDateTime startsAt = LocalDateTime.ofInstant(screening.getStartsAt().toInstant(), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(1)));
		LocalDateTime finishesAt = startsAt.plusHours(movieDuration/60).plusMinutes(movieDuration%60);
		Date finishesAtAsDate = Date.from(finishesAt.toInstant(ZoneOffset.ofHours(1)));
		screening.setFinishesAt(finishesAtAsDate);
	}
	
	public void createScreening() throws IOException{
		Collection<RoomSeat> roomSeats = roomSeatService.findRoomSeatsByRoomId(screening.getRoom().getId());
		//TODO startsDate gets reseted to 1970 along the way i don't know how to fix this issue (temporary fix)
		screening.setStartsAt(DateUtil.setDateTime(screening.getDate(), screening.getStartsAt())); 
		screeningService.save(screening);
		screeningSeatService.saveRoomSeatsAsScreeningSeats(roomSeats, screening);
		init();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Screening created successfully."));
		externalContext.getFlash().setKeepMessages(true);
		externalContext.redirect("/employee/screenings");
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
	
}
