package com.tathn.cinema.web.beans.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.tathn.cinema.domain.movie.MovieService;

@Named("movieConverter")
public class MovieConverter implements Converter {

	@EJB
    MovieService movieService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return movieService.findByTitle(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
