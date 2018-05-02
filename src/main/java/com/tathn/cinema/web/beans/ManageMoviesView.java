package com.tathn.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.tathn.cinema.domain.movie.Movie;
import com.tathn.cinema.domain.movie.MovieService;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

public class ManageMoviesView implements Serializable {

	private static final long serialVersionUID = -9062642633257589248L;
	
	private Collection<Movie> movies;
	private Collection<Movie> filteredMovies;
	
	private Movie selectedMovie;
	
	private Movie movie;
	
	@Autowired
	private MovieService movieService;
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		movies = movieService.findAll();
		filteredMovies = new ArrayList<Movie>();
		movie = new Movie();
	}
	
	public void resetMovie(){
		movie = new Movie();
	}
	
	public void reloadMovies(){
		movies = movieService.findAll();
	}

	public void save() throws IOException{
		movieService.save(movie);
		resetMovie();
		reloadMovies();
		RequestContext.getCurrentInstance().execute("PF('addMovieDialog').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movie saved successfully."));
	}
	
	public void initEditDialog(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long movieId = Long.parseLong((params.get("movie_id")));
		movie = movieService.findById(movieId);
	}
	
	public void showOne(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long movieId = Long.parseLong((params.get("movie_id")));
		for (Movie movie : movies) {
			if(movie.getId().equals(movieId)){
				selectedMovie = movie;
				break;
			}
		}
	}
	
	public void delete(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long movieId = Long.parseLong((params.get("movie_id")));
		movieService.delete(movieId);
		reloadMovies();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movie deleted successfully."));
	}
	
	public Collection<Movie> getMovies() { return movies; }
	public void setMovies(Collection<Movie> movies) { this.movies = movies; }

	public Collection<Movie> getFilteredMovies() { return filteredMovies; }
	public void setFilteredMovies(Collection<Movie> filteredMovies) { this.filteredMovies = filteredMovies; }

	public Movie getSelectedMovie() { return selectedMovie;}
	public void setSelectedMovie(Movie selectedMovie) { this.selectedMovie = selectedMovie; }

	public Movie getMovie() { return movie; }
	public void setMovie(Movie movie) { this.movie = movie; }
}
