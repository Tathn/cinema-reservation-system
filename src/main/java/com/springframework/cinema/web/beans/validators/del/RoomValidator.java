package com.springframework.cinema.web.beans.validators.del;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomRepository;
import com.springframework.cinema.domain.room.RoomService;

public class RoomValidator implements Validator {

	private final RoomService roomService;
	
	public RoomValidator(RoomRepository roomRepository) {
		roomService = new RoomService(roomRepository);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Room.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
		Room room = (Room) object;
		if(roomService.checkIfNameExists(room.getName()))
			errors.rejectValue("name", "name.already_exists");
		if(room.getColumns() < 1)
			errors.rejectValue("columns", "columns.less_than_one");
		if(room.getRows() < 1)
			errors.rejectValue("rows", "rows.less_than_one");
	}

}
