package com.springframework.cinema.web;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springframework.cinema.domain.screening.ScreeningRepository;
import com.springframework.cinema.domain.screening.ScreeningService;

/**
 * Created by Patryk on 2017-05-23.
 */

@Controller
public class CinemaController {
	
	private final ScreeningService screeningService;
	
	@Autowired
	public CinemaController(ScreeningRepository screeningRepository) {
		screeningService = new ScreeningService(screeningRepository);
	}
	
	@GetMapping("/")
	public String getMainPageView(Model model){
		model.addAttribute("screenings", screeningService.findByDate(Calendar.getInstance().getTime()));
		return "index";
	}
}
