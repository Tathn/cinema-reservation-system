package com.tathn.cinema.domain.movie;

import java.util.Collection;

import com.tathn.cinema.infrastructure.model.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface MovieRepository extends BaseRepository<Movie, Long> {
	Movie findMovieByTitle(String title);
	Collection<Movie> findMoviesByAvailable(boolean available);

}
