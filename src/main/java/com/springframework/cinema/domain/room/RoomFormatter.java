package com.springframework.cinema.domain.room;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.springframework.cinema.domain.movie.Movie;

public class RoomFormatter implements Formatter<Room> {

	private final RoomService roomService;
	
	public RoomFormatter(RoomService roomService){
		this.roomService = roomService;
	}
	
	@Override
	public String print(Room room, Locale locale) {
		return room.getName();
	}

	@Override
	public Room parse(String roomId, Locale locale) throws ParseException {
		Room room = roomService.findById(Long.valueOf(roomId));
		if(room == null)
			throw new ParseException("Failed to parse Room. Room with id " + roomId + " was not found in database.", -2);
		return room;
	}

}
