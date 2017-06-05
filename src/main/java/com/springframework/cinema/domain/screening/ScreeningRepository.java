package com.springframework.cinema.domain.screening;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-06-03.
 */
@Repository
public interface ScreeningRepository extends BaseRepository<Screening, Long> {

}
