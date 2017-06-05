package com.springframework.cinema.domain.ticket;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface TicketRepository extends BaseRepository<Ticket, Long> {
    

}
