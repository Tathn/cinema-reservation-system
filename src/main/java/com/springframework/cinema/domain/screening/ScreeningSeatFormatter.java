package com.springframework.cinema.domain.screening;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

public class ScreeningSeatFormatter implements Formatter<ScreeningSeat> {

	private final ScreeningSeatService screeningSeatService;
	
	public ScreeningSeatFormatter(ScreeningSeatService screeningSeatService) {
		this.screeningSeatService = screeningSeatService;
	}
	
	@Override
	public String print(ScreeningSeat screeningSeat, Locale locale) {
		return screeningSeat.getLabel();
	}

	@Override
	public ScreeningSeat parse(String screeningSeatId, Locale locale) throws ParseException {
		ScreeningSeat screeningSeat = screeningSeatService.findById(Long.valueOf(screeningSeatId));
		if(screeningSeat == null)
			throw new ParseException("Failed to parse ScreeningSeat. ScreeningSeat with id " + screeningSeatId + " was not found in database.", -2);
		return screeningSeat;
	}

}
