package com.cinema.service;

import com.cinema.domain.Ticket;
import com.cinema.domain.TicketRepository;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
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
