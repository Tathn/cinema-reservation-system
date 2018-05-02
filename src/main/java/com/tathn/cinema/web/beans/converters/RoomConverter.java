package com.tathn.cinema.web.beans.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.tathn.cinema.domain.room.RoomService;

@Named("roomConverter")
public class RoomConverter implements Converter {

	@EJB
    RoomService roomService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return roomService.findByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
