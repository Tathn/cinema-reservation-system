package com.tathn.cinema.web.beans.converters;

import com.tathn.cinema.domain.room.RoomService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("roomConverter")
public class RoomConverter implements Converter {

    private RoomService roomService;

    @Inject
	public RoomConverter(RoomService roomService) {
    	this.roomService = roomService;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return roomService.findByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
