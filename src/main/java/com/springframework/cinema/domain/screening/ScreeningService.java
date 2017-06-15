package com.springframework.cinema.domain.screening;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
    
    public Collection<Screening> findByDate(Date date) {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.format(date);
    	return screeningRepository.findScreeningsByDate(date);
    }
}
