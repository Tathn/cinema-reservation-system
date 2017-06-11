package com.springframework.cinema.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.movie.MovieRepository;
import com.springframework.cinema.domain.movie.MovieService;
import com.springframework.cinema.domain.movie.MovieValidator;

@Controller
public class MovieController {
	
	private static final String VIEWS_MOVIE_CREATE_OR_UPDATE_FORM= "movie/createOrUpdateMovieForm";
	
	private final MovieService movieService;
	private final MovieValidator movieValidator;
	
	@Autowired
	public MovieController(MovieRepository movieRepository) {
		movieService = new MovieService(movieRepository);
		movieValidator = new MovieValidator(movieRepository);
	}
	
	@InitBinder
	public void initMovieBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(movieValidator);
	}
	
	@GetMapping("/admin/movies")
    public String getManageMoviesView(Model model){
    	Collection<Movie> movies = movieService.findAll();
    	model.addAttribute("movies", movies);
    	return "movie/manageMovies";
    }
    
    @GetMapping("/admin/movies/create")
    public String initCreateMovieForm(@ModelAttribute Movie movie) {
    	return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/admin/movies/create")
    public String processCreateMovieForm(@Valid Movie movie, BindingResult result, RedirectAttributes redirect) {
    	if(result.hasErrors()) {
    		return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
    	} else {
    		movieService.save(movie);
            redirect.addFlashAttribute("globalMessage", "Successfully created new movie");
            return "redirect:/admin/movies";
    	}
    }
    
    @GetMapping("/admin/movies/edit/{movieId}")
    public String initUpdateMovieForm(@PathVariable Long movieId, Model model){
    	Movie movie = movieService.findById(movieId);
        model.addAttribute("movie", movie);
        return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/admin/movies/edit/{movieId}")
    public String processUpdateMovieForm(@Valid Movie movie, @PathVariable Long movieId, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return VIEWS_MOVIE_CREATE_OR_UPDATE_FORM;
        } else {
        	movie.setId(movieId);
        	movieService.save(movie);
            redir.addFlashAttribute("globalMessage","Movie saved successfully!");
            return "redirect:/admin/movies";
        }
    }
    
    @PostMapping("/admin/movies/delete/{movieId}")
    public String deleteMovie(@PathVariable Long movieId, RedirectAttributes redir) {
    	movieService.delete(movieId);
    	redir.addFlashAttribute("globalMessage","Movie removed successfully!");
    	return "redirect:/admin/movies";
    }
}
