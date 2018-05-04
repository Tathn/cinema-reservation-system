package com.tathn.cinema.web.beans.validators;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.tathn.cinema.domain.movie.Movie;
import org.primefaces.validate.ClientValidator;

@Named("movieAttributesValidator")
public class MovieAttributesValidator implements Validator, ClientValidator { 

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
		
		Movie movie = (Movie) uiComponent.getAttributes().get("movie");

		if(!movie.getThreeDimensional() && !movie.getTwoDimensional())
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "No attribute selected",
    				"Please choose one option from 2D and 3D."));
	}
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.MovieAttributesValidator";
	}
	
	
}
