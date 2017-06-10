package com.springframework.cinema.domain.movie;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service
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

    public Movie findById(Long id){
        return movieRepository.findOne(id);
    }
    
    public Movie findByTitle(String title) {
    	return movieRepository.findMovieByTitle(title);
    }
}
