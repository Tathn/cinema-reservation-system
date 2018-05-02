package com.tathn.cinema.web.beans.validators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.primefaces.validate.ClientValidator;

@Named("screeningDateValidator")
public class ScreeningDateValidator implements Validator, ClientValidator {

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
		
		LocalDate valueDate = LocalDateTime.ofInstant(((Date)value).toInstant(), ZoneId.systemDefault()).toLocalDate();
		if( valueDate.isBefore(LocalDate.now()))
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors",
					"You cannot create a screening in the past."));
	}
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.ScreeningDateValidator";
	}
	
	
}
