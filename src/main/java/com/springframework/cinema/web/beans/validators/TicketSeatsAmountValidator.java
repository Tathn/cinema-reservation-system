package com.springframework.cinema.web.beans.validators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.primefaces.validate.ClientValidator;

import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.user.UserService;

@Named("ticketSeatsAmountValidator")
public class TicketSeatsAmountValidator implements Validator, ClientValidator {

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
		
		Ticket ticket = (Ticket) uiComponent.getAttributes().get("ticket");
		if(ticket.getScreeningSeats().isEmpty())
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "No seats selected.",
					"Select at least one seat to book a ticket."));
	}
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.TicketSeatsAmountValidator";
	}
	
	
}
