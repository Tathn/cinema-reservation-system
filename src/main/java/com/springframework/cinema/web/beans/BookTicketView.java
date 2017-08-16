package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.jsf.FacesContextUtils;

import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningSeat;
import com.springframework.cinema.domain.screening.ScreeningSeatService;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.domain.user.CustomUserDetails;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityUtil;

public class BookTicketView implements Serializable {
private static final long serialVersionUID = -3210711807003424547L;
	
	private Screening selectedScreening;
	private Collection<ScreeningSeat> selectedScreeningSeats;
	private Ticket ticket;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ScreeningService screeningService;

	@Autowired
	private ScreeningSeatService screeningSeatService;
	
	@Autowired
	private TicketService ticketService;

	public BookTicketView() {}
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		Long screeningId = (Long)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("screeningId");
		selectedScreening = screeningService.findById(screeningId);
		selectedScreeningSeats = screeningSeatService.findScreeningSeatsByScreeningId(screeningId);
		ticket = new Ticket();
		ticket.setScreeningSeats(new ArrayList<ScreeningSeat>());
	}
	
	public void redirectIfScreeningDataEmpty() throws IOException{
		if(selectedScreening == null || selectedScreeningSeats == null)
			FacesContext.getCurrentInstance().getExternalContext().redirect("/");
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
		
		if(selectedSeat != null && !seatAlreadyChoosen){
			selectedSeat.setPrice(selectedScreening.getAdultTicketPrice());
			ticket.getScreeningSeats().add(selectedSeat);
			calculateTicketPrice();
		} else if(seatAlreadyChoosen){
			ticket.getScreeningSeats().remove(selectedSeat);
			calculateTicketPrice();
		}
	}
	
	public void calculateTicketPrice(){
		float ticketPrice = 0;
		for(ScreeningSeat seat : ticket.getScreeningSeats())
			ticketPrice += seat.getPrice();
		ticket.setPrice(ticketPrice);
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
				ticket.setScreening(selectedScreening);
				ticket.setPaid(false);
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
	
	public Collection<ScreeningSeat> getSelectedScreeningSeats() { return selectedScreeningSeats; }
	public void setSelectedScreeningSeats(Collection<ScreeningSeat> selectedScreeningSeats) { this.selectedScreeningSeats = selectedScreeningSeats; }

	public Screening getSelectedScreening() { return selectedScreening; }
	public void setSelectedScreening(Screening selectedScreening) { this.selectedScreening = selectedScreening; }

	public Ticket getTicket() { return ticket; }
	public void setTicket(Ticket ticket) { this.ticket = ticket; }
}
