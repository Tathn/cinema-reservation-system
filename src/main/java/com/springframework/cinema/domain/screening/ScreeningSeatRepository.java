package com.springframework.cinema.domain.screening;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-06-03.
 */
@Repository
public interface ScreeningSeatRepository extends BaseRepository<ScreeningSeat, Long> {
    Collection<ScreeningSeat> findScreeningSeatsByScreeningId(Long screeningId);
    
    @Transactional
    void deleteByScreeningId(Long screeningId);
}
