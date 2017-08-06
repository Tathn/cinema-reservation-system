package com.springframework.cinema.domain.ticket;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.springframework.cinema.domain.screening.ScreeningSeat;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }
    
    public void deleteByScreeningSeatsId(Long screeningSeatId){
    	ticketRepository.deleteByScreeningSeatsId(screeningSeatId);
    }

    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public void delete(Long id){
        ticketRepository.delete(id);
    }

    public Collection<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id){
        return ticketRepository.findOne(id);
    }
}
