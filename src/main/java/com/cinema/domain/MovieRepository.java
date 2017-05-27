package com.cinema.domain;

import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface MovieRepository extends BaseRepository<Movie, Long> {
    Movie findMovieByTitle(String title);

}
