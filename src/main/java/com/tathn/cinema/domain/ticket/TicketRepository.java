package com.tathn.cinema.domain.ticket;

import java.util.Collection;

import javax.transaction.Transactional;

import com.tathn.cinema.infrastructure.model.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface TicketRepository extends BaseRepository<Ticket, Long> {
	@Transactional
    void deleteByScreeningSeatsId(Long screeningSeatId);
	@Transactional
	void deleteByUserId(Long userId);
	Collection<Ticket> findByUserId(Long userId);

}
