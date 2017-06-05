package com.springframework.cinema.domain.screening;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.springframework.cinema.domain.ticket.Ticket;
import com.springframework.cinema.infrastructure.model.BaseEntity;

/**
 * Created by Patryk on 2017-06-03.
 */
@Entity
@Table(name="screenings")
public class Screening extends BaseEntity {

	private static final long serialVersionUID = 3510289568546131848L;
	@ManyToOne
	@JoinColumn(name = "movie_fk")
	Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "room_fk")
	Room room;
	
	@OneToMany(mappedBy = "screening")
	Collection<Ticket> tickets;
	

	@OneToMany(mappedBy = "screening")
	Collection<ScreeningSeat> seats;

}
