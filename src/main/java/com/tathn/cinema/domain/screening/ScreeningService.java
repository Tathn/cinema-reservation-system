package com.tathn.cinema.domain.screening;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tathn.cinema.domain.room.Room;

/**
 * Created by Patryk on 2017-06-03.
 */
@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;

    @Autowired
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
    
    public Collection<Screening> findAllSortByStartsAt(){
    	ArrayList<Screening> screenings = (ArrayList<Screening>) screeningRepository.findAll();
    	screenings.sort(new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				return o1.getStartsAt().compareTo(o2.getStartsAt());
			}
		});
    	return screenings;
    }

    public Screening findById(Long id){
        return screeningRepository.findOne(id);
    }
    
    public Collection<Screening> findByDate(Date date) {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.format(date);
    	return screeningRepository.findScreeningsByDate(date);
    }
    
    public Collection<Screening> findByDateAndRoom(Date date, Room room) {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.format(date);
    	return screeningRepository.findScreeningsByDateAndRoom(date, room);
    }
}
