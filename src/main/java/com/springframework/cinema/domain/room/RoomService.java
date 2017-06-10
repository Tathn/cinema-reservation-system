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
    
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }

    public void delete(Long id){
        roomRepository.delete(id);
    }

    public Collection<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room findById(Long id){
        return roomRepository.findOne(id);
    }
    
}
