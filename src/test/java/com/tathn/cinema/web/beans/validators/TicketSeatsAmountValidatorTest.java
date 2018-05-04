package com.tathn.cinema.web.beans.validators;

import com.tathn.cinema.domain.screening.ScreeningSeat;
import com.tathn.cinema.domain.ticket.Ticket;
import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketSeatsAmountValidatorTest {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final TicketSeatsAmountValidator ticketSeatsAmountValidator = new TicketSeatsAmountValidator();

    private void validateTicket(Collection<ScreeningSeat> screeningSeats) {
        Ticket ticket = new Ticket();
        ticket.setScreeningSeats(screeningSeats);
        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("ticket", ticket);
        when(uiComponent.getAttributes()).thenReturn(attributesMap);
        ticketSeatsAmountValidator.validate(facesContext, uiComponent, new Object());
    }

    @Test(expected = ValidatorException.class)
    public void testValidateTicketWithoutSeats() {
        validateTicket(Collections.emptyList());
    }

    @Test
    public void testValidateTicketWithSeat() {
        List<ScreeningSeat> seats = new ArrayList<>();
        seats.add(mock(ScreeningSeat.class));
        validateTicket(seats);
    }

}