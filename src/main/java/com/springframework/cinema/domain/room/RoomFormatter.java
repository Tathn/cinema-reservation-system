package com.springframework.cinema.domain.room;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

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
		return room;
	}

}
