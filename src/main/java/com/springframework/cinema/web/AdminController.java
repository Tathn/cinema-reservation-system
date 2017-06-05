package com.cinema.web;

import com.cinema.domain.Movie;
import com.cinema.domain.MovieRepository;
import com.cinema.domain.Role;
import com.cinema.domain.RoleRepository;
import com.cinema.domain.User;
import com.cinema.domain.UserRepository;
import com.cinema.service.MovieService;
import com.cinema.service.RoleService;
import com.cinema.service.SecurityService;
import com.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patryk on 2017-04-19.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final String VIEWS_MOVIE_CREATE_OR_UPDATE_FORM= "movie/createOrUpdateMovieForm";
	private static final String VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM= "employee/createOrUpdateEmployeeForm";
	
	
    private final UserService userService;
    private final RoleService roleService;
    private final SecurityService securityService;
    private final MovieService movieService;
    
    @Autowired
    public AdminController(UserRepository userRepository, RoleRepository roleRepository, MovieRepository movieRepository){
        userService = new UserService(userRepository);
        roleService = new RoleService(roleRepository);
        securityService = new SecurityService();
        movieService = new MovieService(movieRepository);
    }
    
    @GetMapping
    public String getAdminPanel(){
        return "admin/adminPanel";
    }
    
    private Collection<User> populateUsersByRole(String roleName) {
    	Role role = roleService.findByName(roleName);
    	Collection<User> users = userService.findUsersByRoles(role);
    	return users;
    }

    @GetMapping("users")
    public String getUsers(Model model) {
    	Collection<User> users = populateUsersByRole("ROLE_USER");
        model.addAttribute("users",users);
    	return "user/manageUsers";
    }
    
    @GetMapping("users/employees")
    public String getEmployees(Model model) {
    	Collection<User> employees = populateUsersByRole("ROLE_EMPLOYEE");
        model.addAttribute("employees",employees);
    	return "employee/manageEmployees";
    }
    
    @GetMapping("users/employees/create")
    public String initCreateEmployeeForm(@ModelAttribute User user){
    	return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("users/employees/create")
    public String processCreateEmployeeForm(@ModelAttribute User user, BindingResult result, RedirectAttributes redirect){
    	if(result.hasErrors()) {
    		return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
    	} else {
    		Collection<Role> userRoles = new ArrayList<Role>();
    		userRoles.add(roleService.findByName("ROLE_EMPLOYEE"));
    		userRoles.add(roleService.findByName("ROLE_USER"));
        	user.setRoles(userRoles);
        	String encodedPassword = securityService.encodePassword(user.getPassword());
        	user.setPassword(encodedPassword);
            user = userService.save(user);
            redirect.addFlashAttribute("globalMessage", "Successfully created an Employee Account");
    		return "redirect:/admin/users/employees";
    	}
    }
    
    @PostMapping("users/delete/{userId}")
    public String removeUser(@PathVariable Long userId, RedirectAttributes redir){
        userService.delete(userId);
        redir.addFlashAttribute("globalMessage","User removed successfully!");
        return "redirect:/admin/users";
    }
    
    @GetMapping("movies")
    public String getMovies(Model model){
    	Collection<Movie> movies = movieService.findAll();
    	model.addAttribute("movies", movies);
    	return "movie/manageMovies";
    }
    
    @GetMapping("movies/create")
    public String initCreateMovieForm(@ModelAttribute Movie movie) {
    	return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("movies/create")
    public String processCreateMovieForm(@ModelAttribute Movie movie, BindingResult result, RedirectAttributes redirect) {
    	if(result.hasErrors()) {
    		return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
    	} else {
    		movieService.save(movie);
            redirect.addFlashAttribute("globalMessage", "Successfully created new movie");
            return "redirect:/admin/movies";
    	}
    }
    
    @GetMapping("movies/edit/{movieId}")
    public String initUpdateMovieForm(@PathVariable Long movieId, Model model){
    	Movie movie = movieService.findById(movieId);
        model.addAttribute("movie", movie);
        return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("movies/edit/{movieId}")
    public String processUpdateMovieForm(@ModelAttribute Movie movie, @PathVariable Long movieId, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
        } else {
        	movie.setId(movieId);
        	movieService.save(movie);
            redir.addFlashAttribute("globalMessage","Movie saved successfully!");
            return "redirect:/admin/movies";
        }
    }
    
    @PostMapping("movies/delete/{movieId}")
    public String deleteMovie(@PathVariable Long movieId, RedirectAttributes redir) {
    	movieService.delete(movieId);
    	redir.addFlashAttribute("globalMessage","Movie removed successfully!");
    	return "redirect:/admin/movies";
    }
}
