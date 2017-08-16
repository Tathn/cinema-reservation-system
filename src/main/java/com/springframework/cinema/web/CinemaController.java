package com.springframework.cinema.web;

import java.util.Calendar;
import java.util.Collection;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.screening.ScreeningRepository;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.user.User;

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
	
	@GetMapping("/account/edit")
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
	
	@GetMapping("/ticket/book/{screeningId}")
	public String initBookTicketForm(@PathVariable Long screeningId){
		return "pages/bookTicket";
	}
	
	@GetMapping("/ticket/manage")
	public String initManageTicketsView(){
		return "pages/manageTickets";
	}
	
}
