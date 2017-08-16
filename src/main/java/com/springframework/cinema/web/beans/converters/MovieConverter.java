package com.springframework.cinema.web.beans.converters;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.movie.MovieService;
import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomService;

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
