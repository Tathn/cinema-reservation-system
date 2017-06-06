package com.springframework.cinema.domain.room;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service
public class RoomSeatService {
    private final RoomSeatRepository roomSeatRepository;

    public RoomSeatService(RoomSeatRepository roomSeatRepository){
        this.roomSeatRepository = roomSeatRepository;
    }

    public RoomSeat save(RoomSeat roomSeat){
        return roomSeatRepository.save(roomSeat);
    }
    
    public void save(Collection<RoomSeat> roomSeats) {
    	for(RoomSeat roomSeat : roomSeats){
    		roomSeatRepository.save(roomSeat);
    	}
    }

    public void delete(Long id){
        roomSeatRepository.delete(id);
    }

    public Collection<RoomSeat> findAll(){
        return roomSeatRepository.findAll();
    }

    public RoomSeat findById(Long id){
        return roomSeatRepository.findOne(id);
    }
    
    public Collection<RoomSeat> findRoomSeatsByRoomId(Long roomId) {
    	return roomSeatRepository.findRoomSeatsByRoomId(roomId);
    }
    
    public void deleteByRoomId(Long roomId) {
    	roomSeatRepository.deleteByRoomId(roomId);
    }
}
