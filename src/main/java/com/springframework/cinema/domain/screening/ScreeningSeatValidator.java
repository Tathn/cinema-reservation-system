package com.springframework.cinema.domain.screening;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ScreeningSeatValidator implements Validator {

	private final ScreeningSeatService screeningSeatService;
	
	public ScreeningSeatValidator(ScreeningSeatService screeningSeatService) {
		this.screeningSeatService = screeningSeatService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ScreeningSeat.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ScreeningSeat screeningSeat = (ScreeningSeat) object;
		if(!screeningSeat.getAvailability())
			errors.rejectValue("availability", "seat.notavailable");
	}

}
