package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.infrastructure.util.SecurityUtil;

public class ManageTicketsView implements Serializable {

	private static final long serialVersionUID = -5164627202620142850L;

	private Collection<Ticket> tickets;
	private Collection<Ticket> filteredTickets;
	
	@Autowired
	private TicketService ticketService;
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		updateTicketsList();
	}
	
	public void updateTicketsList(){
		tickets = ticketService.findCurrentUserTickets();
	}
	
	public void payForTicket(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long ticketId = Long.parseLong((params.get("ticket_id")));
		Ticket ticket = ticketService.findById(ticketId);
		ticket.setPaid(true);
		ticketService.save(ticket);
		updateTicketsList();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Payment status for ticket #" + ticket.getId() + ": successfull"));
	}

	public Collection<Ticket> getTickets() { return tickets; }
	public void setTickets(Collection<Ticket> tickets) { this.tickets = tickets; }

	public Collection<Ticket> getFilteredTickets() { return filteredTickets; }
	public void setFilteredTickets(Collection<Ticket> filteredTickets) { this.filteredTickets = filteredTickets; }

	public TicketService getTicketService() { return ticketService; }
	public void setTicketService(TicketService ticketService) { this.ticketService = ticketService; }
}
