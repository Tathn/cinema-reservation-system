package com.tathn.cinema.web.beans.validators;

import com.tathn.cinema.web.beans.validators.PasswordValidator;
import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PasswordValidatorTests {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final PasswordValidator passwordValidator = new PasswordValidator();

    private void validatePassword(String pass, String passConfirm) {
        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("password", pass);
        attributesMap.put("passwordConfirm", passConfirm);
        when(uiComponent.getAttributes()).thenReturn(attributesMap);
        passwordValidator.validate(facesContext, uiComponent, new Object());
    }

    @Test(expected = ValidatorException.class)
    public void testValidatePasswordsDoNotMatch() {
        validatePassword("password", "differentPassword");
    }

    @Test(expected = ValidatorException.class)
    public void testValidatePasswordTooShort() {
        validatePassword("pass", "pass");
    }

    @Test
    public void testValidatePasswordCorrect() {
        validatePassword("password", "password");
    }

}
