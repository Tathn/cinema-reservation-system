package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.infrastructure.util.SecurityUtil;

@Component("ticketBean")
@Scope("session")
public class TicketBean implements Serializable {

	private static final long serialVersionUID = -5164627202620142850L;

	private Collection<Ticket> tickets;
	private Collection<Ticket> filteredTickets;
	
	@Inject
	private TicketService ticketService;
	
	@PostConstruct
	public void init(){
//		if(SecurityUtil.isAuthenticated()){
//			
//		} else {
//			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//	        FacesContext.getCurrentInstance().addMessage("logInMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","You are not logged in. Please log in to browse your tickets."));
//			externalContext.getFlash().setKeepMessages(true);
//			try {
//				externalContext.redirect("/login");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
		tickets = ticketService.findCurrentUserTickets();
	}

	public Collection<Ticket> getTickets() { return tickets; }
	public void setTickets(Collection<Ticket> tickets) { this.tickets = tickets; }

	public Collection<Ticket> getFilteredTickets() { return filteredTickets; }
	public void setFilteredTickets(Collection<Ticket> filteredTickets) { this.filteredTickets = filteredTickets; }

	public TicketService getTicketService() { return ticketService; }
	public void setTicketService(TicketService ticketService) { this.ticketService = ticketService; }
}
