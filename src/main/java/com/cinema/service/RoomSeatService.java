package com.cinema.service;

import com.cinema.domain.Seat;
import com.cinema.domain.SeatRepository;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    public Seat save(Seat seat){
        return seatRepository.save(seat);
    }

    public void delete(Long id){
        seatRepository.delete(id);
    }

    public Collection<Seat> findAll(){
        return seatRepository.findAll();
    }

    public Seat findById(Long id){
        return seatRepository.findOne(id);
    }
}
