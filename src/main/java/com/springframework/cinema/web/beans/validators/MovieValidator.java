package com.springframework.cinema.web.beans.validators;

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

import com.springframework.cinema.domain.user.UserService;

@Named("movieValidator")
public class MovieValidator implements Validator, ClientValidator { 
	
	@EJB
	private UserService userService;

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
		
//		if(value.toString().length() < USERNAME_MIN_LENGTH )
//			 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors.",
//					 "Username is too short."));
		
		if(userService.checkIfEmailExists(value.toString()))
			 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors.",
					 "Email is already taken, please choose another one."));
	}
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.EmailValidator";
	}
	
	
}
