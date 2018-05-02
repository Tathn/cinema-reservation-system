package com.tathn.cinema.domain.screening;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.tathn.cinema.domain.room.RoomSeat;

/**
 * Created by Patryk on 2017-06-03.
 */
@Service
public class ScreeningSeatService {
    private final ScreeningSeatRepository screeningSeatRepository;

    public ScreeningSeatService(ScreeningSeatRepository screeningSeatRepository){
        this.screeningSeatRepository = screeningSeatRepository;
    }

    
    public void saveRoomSeatsAsScreeningSeats(Collection<RoomSeat> roomSeats, Screening screening) {
    	for (RoomSeat roomSeat : roomSeats) {
    		screeningSeatRepository.save(new ScreeningSeat(roomSeat, screening));
    	}
    }
    
    public Collection<ScreeningSeat> findScreeningSeatsByScreeningId(Long screeningId) {
    	return screeningSeatRepository.findScreeningSeatsByScreeningId(screeningId);
    }
    
    public void save(ScreeningSeat seat){
    	screeningSeatRepository.save(seat);
    }
    
    public void delete(Long id){
        screeningSeatRepository.delete(id);
    }
    
    public void deleteByScreeningId(Long screeningId) {
    	screeningSeatRepository.deleteByScreeningId(screeningId);
    }

    public Collection<ScreeningSeat> findAll(){
        return screeningSeatRepository.findAll();
    }

    public ScreeningSeat findById(Long id){
        return screeningSeatRepository.findOne(id);
    }
}
