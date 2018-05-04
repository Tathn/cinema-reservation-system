package com.tathn.cinema.web.beans.validators;

import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ScreeningDateValidatorTests {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final ScreeningDateValidator screeningDateValidator = new ScreeningDateValidator();

    private void validateDate(LocalDateTime dateTime) {
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        screeningDateValidator.validate(facesContext, uiComponent, date);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateDateInThePast() {
        validateDate(LocalDateTime.now().minusDays(1));
    }

    @Test
    public void testValidateDateNow() {
        validateDate(LocalDateTime.now());
    }

    @Test
    public void testValidateDateInTheFuture() {
        validateDate(LocalDateTime.now().plusDays(1));
    }
}