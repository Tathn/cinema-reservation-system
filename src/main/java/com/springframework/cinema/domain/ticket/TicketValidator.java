package com.springframework.cinema.domain.ticket;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TicketValidator implements Validator {

	private final TicketService ticketService;
	
	public TicketValidator(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Ticket.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Ticket ticket = (Ticket) object;
		if(ticket.getScreeningSeats() == null)
			errors.rejectValue("screeningSeats", "screeningSeats.empty");
	}

}
