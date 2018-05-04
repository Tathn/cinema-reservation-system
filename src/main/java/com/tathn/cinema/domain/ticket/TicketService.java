package com.tathn.cinema.domain.ticket;

import java.util.ArrayList;
import java.util.Collection;

import com.tathn.cinema.domain.screening.ScreeningSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tathn.cinema.domain.screening.ScreeningSeatService;
import com.tathn.cinema.domain.user.User;

/**
 * Created by Patryk on 2017-05-27.
 */
@Service("ticketService")
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ScreeningSeatService screeningSeatService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ScreeningSeatService screeningSeatService){
        this.ticketRepository = ticketRepository;
        this.screeningSeatService = screeningSeatService;
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
    
    public void deleteByUserId(Long userId){
    	Collection<Ticket> userTickets = ticketRepository.findByUserId(userId);
    	ArrayList<ScreeningSeat> ticketsScreeningSeats = new ArrayList<>();
    	for(Ticket ticket : userTickets){
    		ticketsScreeningSeats.addAll(ticket.getScreeningSeats());
    	}
    	for(ScreeningSeat seat : ticketsScreeningSeats){
    		seat.setAvailability(true);
    		screeningSeatService.save(seat);
    	}
    	ticketRepository.deleteByUserId(userId);
    }

    public Collection<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id){
        return ticketRepository.findOne(id);
    }
    
    public Collection<Ticket> findByUserId(Long userId){
    	return ticketRepository.findByUserId(userId);
    }
    
    public Collection<Ticket> findCurrentUserTickets(){
    	User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return ticketRepository.findByUserId(currentUser.getId());
    }
}
