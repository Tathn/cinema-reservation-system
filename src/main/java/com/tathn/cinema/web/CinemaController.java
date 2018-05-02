package com.tathn.cinema.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Patryk on 2017-05-23.
 */

@Controller
public class CinemaController {
	
	public CinemaController() {
	}
	
	@GetMapping("/")
	public String getMainPageView(){
		return "pages/browseScreenings";
	}
	
	@GetMapping("/register")
	public String initUserCreationForm(){
		return "pages/signup";
	}
	
	@GetMapping("/login")
    public String initLoginForm(){
        return "pages/login";
    }
	
	@GetMapping("/account")
    public String initEditAccountForm(){
        return "pages/updateUser";
    }
	
	@GetMapping("/admin/users")
    public String getUsers() {
    	return "pages/manageUsers";
    }
	
	@GetMapping("/admin/movies")
    public String getManageMoviesView(){
    	return "pages/manageMovies";
    }
	
	@GetMapping("/employee/screenings")
	public String getManageScreeningsView() {
		return "pages/manageScreenings";
	}
	
	@GetMapping("/screening/{screeningId}")
	public String initBookTicketForm(@PathVariable Long screeningId){
		return "pages/bookTicket";
	}
	
	@GetMapping("/tickets")
	public String initManageTicketsView(){
		return "pages/manageTickets";
	}
	
}
