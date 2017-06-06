package com.springframework.cinema.domain.room;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service
public class RoomService {
    
	private final RoomRepository roomRepository;
    private final RoomSeatService roomSeatService;
    
    public RoomService(RoomRepository roomRepository, RoomSeatRepository roomSeatRepository){
        this.roomRepository = roomRepository;
        roomSeatService = new RoomSeatService(roomSeatRepository);
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }

    public void delete(Long id){
    	roomSeatService.deleteByRoomId(id);
        roomRepository.delete(id);
    }

    public Collection<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room findById(Long id){
        return roomRepository.findOne(id);
    }
}
