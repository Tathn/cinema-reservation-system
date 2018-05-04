package com.tathn.cinema.web.beans.validators;

import com.tathn.cinema.domain.user.UserService;
import com.tathn.cinema.web.beans.validators.EmailValidator;
import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmailValidatorTests {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final EmailValidator emailValidator;
    private final Object email = mock(Object.class);
    private final UserService userService = mock(UserService.class);

    public EmailValidatorTests() {
        this.emailValidator = new EmailValidator(userService);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNoEmailCharactersInEmail() {
        when(email.toString()).thenReturn("wrongEmail");
        emailValidator.validate(facesContext, uiComponent, email);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateOneEmailCharacterInEmail() {
        for (char c : EmailValidator.VALIDATED_CHARS) {
            when(email.toString()).thenReturn("wrongEmail" + c);
            emailValidator.validate(facesContext, uiComponent, email);
        }
    }

    @Test
    public void testValidateValidEmail() {
        when(email.toString()).thenReturn("goodEmail@email.com");
        when(userService.checkIfEmailExists(email.toString())).thenReturn(false);
        emailValidator.validate(facesContext, uiComponent, email);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateEmailAlreadyExist() {
        when(email.toString()).thenReturn("goodEmail@email.com");
        when(userService.checkIfEmailExists(email.toString())).thenReturn(true);
        emailValidator.validate(facesContext, uiComponent, email);
    }
}
