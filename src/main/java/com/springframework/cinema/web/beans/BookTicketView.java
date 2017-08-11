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
import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.infrastructure.util.DateUtil;

@Component("bookTicketView")
@Scope("session")
public class BookTicketView implements Serializable {
	private static final long serialVersionUID = -3210711807003424547L;
	
	private Screening selectedScreening;
	private Collection<ScreeningSeat> selectedScreeningSeats;
	private Ticket ticket;
	
	@Inject
	private ScreeningService screeningService;
	
	@Inject
	private ScreeningSeatService screeningSeatService;
	
	@Inject
	private TicketService ticketService;

	public BookTicketView(){}
	
	@PostConstruct
	public void init(){
		ticket.setScreeningSeats(new ArrayList<ScreeningSeat>()); 
	}
	
	public void initBuyTicketDialog() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		selectedScreening = screeningService.findById(Long.parseLong((params.get("screening_id"))));
		selectedScreeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(selectedScreening.getId()); 
		ticket.getScreeningSeats().clear();
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
