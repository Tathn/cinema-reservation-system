package com.springframework.cinema.domain.movie;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class MovieFormatter implements Formatter<Movie> {

	private final MovieService movieService;
	
	public MovieFormatter(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@Override
	public String print(Movie movie, Locale locale) {
		return movie.getTitle();
	}

	@Override
	public Movie parse(String movieId, Locale locale) throws ParseException {
		Movie movie = movieService.findById(Long.valueOf(movieId));
		if(movie == null)
			throw new ParseException("Failed to parse Movie. Movie with id " + movieId + " was not found in database.", -2);
		return movie;
	}


}
