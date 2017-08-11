package com.springframework.cinema.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springframework.cinema.domain.screening.ScreeningRepository;
import com.springframework.cinema.domain.screening.ScreeningSeat;
import com.springframework.cinema.domain.screening.ScreeningSeatFormatter;
import com.springframework.cinema.domain.screening.ScreeningSeatRepository;
import com.springframework.cinema.domain.screening.ScreeningSeatService;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.domain.ticket.TicketRepository;
import com.springframework.cinema.domain.ticket.TicketService;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.web.beans.validators.del.ScreeningSeatValidator;
import com.springframework.cinema.web.beans.validators.del.TicketValidator;

@Controller
public class TicketController {
	
	private final TicketService ticketService;
	private final ScreeningSeatService screeningSeatService;
	
	@Autowired
	public TicketController(TicketRepository ticketRepository, ScreeningSeatRepository screeningSeatRepository) {
		ticketService = new TicketService(ticketRepository);
		screeningSeatService = new ScreeningSeatService(screeningSeatRepository);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.addCustomFormatter(new ScreeningSeatFormatter(screeningSeatService));
		binder.addValidators(new TicketValidator(ticketService));
	}
	
	@GetMapping("/ticket/book")
	public String initBookTicketForm(){
		return "ticket/bookTicket";
	}
	
	
	@GetMapping("/ticket/screening/{screeningId}")
	public String initChooseSeatsForm(@PathVariable Long screeningId,@ModelAttribute Ticket ticket, Model model) {
		model.addAttribute("seats", screeningSeatService.findScreeningSeatsByScreeningId(screeningId));
		return "ticket/chooseSeatsForm";
	}
	
	@PostMapping("/ticket/screening/{screeningId}")
	public String processChooseSeatsForm(@PathVariable Long screeningId, @ModelAttribute Ticket ticket, BindingResult result, Model model, RedirectAttributes redir) {
		if (result.hasErrors()) {
			model.addAttribute("seats", screeningSeatService.findScreeningSeatsByScreeningId(screeningId));
            return "ticket/chooseSeatsForm";
        } else {
        	ticket.setUser((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        	for(ScreeningSeat seat : ticket.getScreeningSeats()) {
        		seat.setAvailability(false);
        	}
        	ticketService.save(ticket);
			redir.addFlashAttribute("globalMessage", "Ticket bought successfully");
			return "redirect:/";
        }
	}
	
	//test
	@PostMapping("/ticket/remove/{ticketId}")
	public String deleteTicket(@PathVariable Long ticketId, RedirectAttributes redir){
		Ticket ticket = ticketService.findById(ticketId);
		for(ScreeningSeat seat : ticket.getScreeningSeats()) {
    		seat.setAvailability(true);
    	}
		ticketService.delete(ticketId);
		redir.addFlashAttribute("globalMessage", "Ticket deleted");
		return "redirect:/";
	}
}
