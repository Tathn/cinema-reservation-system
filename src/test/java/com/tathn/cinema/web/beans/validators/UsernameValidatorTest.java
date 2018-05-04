package com.tathn.cinema.web.beans.validators;

import com.tathn.cinema.domain.user.UserService;
import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsernameValidatorTest {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final UsernameValidator usernameValidator;
    private final UserService userService = mock(UserService.class);

    public UsernameValidatorTest() {
        usernameValidator = new UsernameValidator(userService);
    }

    @Test(expected = ValidatorException.class)
    public void userWithThatUsernameAlreadyExists() {
        when(userService.checkIfUsernameExists(anyString())).thenReturn(true);
        usernameValidator.validate(facesContext, uiComponent, "user");
    }
}