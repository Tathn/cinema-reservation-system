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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

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


public class ManageScreeningsView implements Serializable {
	private static final long serialVersionUID = -3210711807003424547L;
	
	private Collection<Screening> screenings;
	private Collection<Screening> filteredScreenings;
	
	private Screening screening;
	private String screeningDimension;
	private String translation;
	private ArrayList<Movie> movies;
	private ArrayList<Room> rooms;
	private String mode;
	
	@Autowired
	private ScreeningService screeningService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomSeatService roomSeatService;
	
	@Autowired
	private ScreeningSeatService screeningSeatService;
	
	@Autowired
	private TicketService ticketService;

	public ManageScreeningsView(){}
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		screenings = screeningService.findAllSortByStartsAt();
		filteredScreenings = new ArrayList<Screening>();
		
		movies = (ArrayList<Movie>) movieService.findAvailable();
		rooms = (ArrayList<Room>) roomService.findAll();
		resetScreening();

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
		//TODO startsDate gets reseted to 1970 along the way i don't know how to fix this issue so i set it again here (temporary fix)
		screening.setStartsAt(DateUtil.setDateTime(screening.getDate(), screening.getStartsAt()));
		screening.setMovieAttributes(screeningDimension + " " + translation);
		if(screening.getId() != null && mode.equals("Update")){
			Screening screeningDbRecord = screeningService.findById(screening.getId());
			if(!screeningDbRecord.getRoom().equals(screening.getRoom()))
			{
				Collection<ScreeningSeat> screeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(screening.getId()); 
				for( ScreeningSeat seat : screeningSeats){
					//TODO Here should be some kind of refund function for customers who already paid for their ticket
					ticketService.deleteByScreeningSeatsId(seat.getId());
				}
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
		for( ScreeningSeat seat : screeningSeats){
			//TODO Here should be some kind of refund function for customers who already paid for their ticket
			ticketService.deleteByScreeningSeatsId(seat.getId());
		}
		screeningSeatService.deleteByScreeningId(screeningId);
		screeningService.delete(screeningId);
		init();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Screening deleted successfully."));
	}
	
	public Collection<Screening> getScreenings() { return screenings; }
	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }
	
	public Collection<Screening> getFilteredScreenings() { return filteredScreenings; }
	public void setFilteredScreenings(Collection<Screening> filteredScreenings) { this.filteredScreenings = filteredScreenings; }

	public Screening getScreening() { return screening; }
	public void setScreening(Screening screening) { this.screening = screening; }

	public String getScreeningDimension() { return screeningDimension; }
	public void setScreeningDimension(String screeningDimension) { this.screeningDimension = screeningDimension; }

	public String getTranslation() { return translation; }
	public void setTranslation(String translation) { this.translation = translation; }

	public Collection<Movie> getMovies() { return movies; }
	public void setMovies(ArrayList<Movie> movies) { this.movies = movies; }
	
	public Collection<Room> getRooms() { return rooms; }
	public void setRooms(ArrayList<Room> rooms) { this.rooms = rooms; }

	public String getMode() { return mode; }
	public void setMode(String mode) { this.mode = mode; }
	
}
