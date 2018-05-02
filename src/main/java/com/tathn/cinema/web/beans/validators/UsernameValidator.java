package com.tathn.cinema.web.beans.validators;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.tathn.cinema.domain.user.UserService;
import org.primefaces.validate.ClientValidator;

@Named("usernameValidator")
public class UsernameValidator implements Validator, ClientValidator {
	
	@EJB
	private UserService userService;

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
			
		if(userService.checkIfUsernameExists(value.toString()))
			 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors.",
					 "Username is already taken, please choose another one."));
	}
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.UsernameValidator";
	}
	
	
}
