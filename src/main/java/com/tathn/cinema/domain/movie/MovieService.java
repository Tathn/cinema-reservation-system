package com.tathn.cinema.domain.movie;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service("movieService")
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Movie save(Movie movie){
        return movieRepository.save(movie);
    }

    public void delete(Long id){
        movieRepository.delete(id);
    }

    public Collection<Movie> findAll(){
        return movieRepository.findAll();
    }
    
    public Collection<Movie> findAvailable() {
    	return movieRepository.findMoviesByAvailable(true);
    }

    public Movie findById(Long id){
        return movieRepository.findOne(id);
    }
    
    public Movie findByTitle(String title) {
    	return movieRepository.findMovieByTitle(title);
    }
    
    public boolean checkIfTitleExists(String title) {
    	return movieRepository.findMovieByTitle(title) != null;
    }
}
