package com.tathn.cinema.web.beans.validators;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.tathn.cinema.domain.user.UserService;
import org.primefaces.validate.ClientValidator;

@Named("emailValidator")
public class EmailValidator implements Validator, ClientValidator{

	public static final char VALIDATED_CHARS[] = { '@',  '.' };

	private UserService userService;

	@Inject
	public EmailValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null || value.toString().isEmpty()) return;
		String email = value.toString();
		for (char c : VALIDATED_CHARS) {
			if(!email.contains(Character.toString(c)))
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors.",
						"Please provide a valid email address."));
		}

		if(userService.checkIfEmailExists(email))
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
