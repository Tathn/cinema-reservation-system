package com.cinema.service;

import com.cinema.domain.ScreeningSeat;
import com.cinema.domain.ScreeningSeatRepository;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-06-03.
 */
@Service
public class ScreeningSeatService {
    private final ScreeningSeatRepository screeningSeatRepository;

    public ScreeningSeatService(ScreeningSeatRepository screeningSeatRepository){
        this.screeningSeatRepository = screeningSeatRepository;
    }

    public ScreeningSeat save(ScreeningSeat screeningSeat){
        return screeningSeatRepository.save(screeningSeat);
    }

    public void delete(Long id){
        screeningSeatRepository.delete(id);
    }

    public Collection<ScreeningSeat> findAll(){
        return screeningSeatRepository.findAll();
    }

    public ScreeningSeat findById(Long id){
        return screeningSeatRepository.findOne(id);
    }
}
