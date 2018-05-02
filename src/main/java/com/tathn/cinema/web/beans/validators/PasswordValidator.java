package com.tathn.cinema.web.beans.validators;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.primefaces.validate.ClientValidator;

@Named("passwordValidator")
public class PasswordValidator implements Validator, ClientValidator { 
	
	private static final int PASSWORD_MIN_LENGTH = 6; 
	
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
		
		String password = (String) uiComponent.getAttributes().get("password");
		String passwordConfirm = (String) uiComponent.getAttributes().get("passwordConfirm");
		if(password.length() < 6 && password.length() != 0)
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors.",
					 "Password is too short. It must have at least " + PASSWORD_MIN_LENGTH + " characters."));
		
		if(!password.equals(passwordConfirm) && password.length() + passwordConfirm.length() > 0)
			 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors.",
					 "Passwords do not match."));
	}
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.PasswordValidator";
	}
	
	
}
