package com.springframework.cinema.domain.movie;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface MovieRepository extends BaseRepository<Movie, Long> {
	Movie findMovieByTitle(String title);

}
