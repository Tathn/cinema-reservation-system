package com.cinema.service;

import com.cinema.domain.Screening;
import com.cinema.domain.ScreeningRepository;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-06-03.
 */
@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository){
        this.screeningRepository = screeningRepository;
    }

    public Screening save(Screening screening){
        return screeningRepository.save(screening);
    }

    public void delete(Long id){
        screeningRepository.delete(id);
    }

    public Collection<Screening> findAll(){
        return screeningRepository.findAll();
    }

    public Screening findById(Long id){
        return screeningRepository.findOne(id);
    }
}
