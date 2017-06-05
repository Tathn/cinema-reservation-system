package com.cinema.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Patryk on 2017-03-06.
 */
@Entity
@Table(name = "screeningSeats")
public class ScreeningSeat extends BaseEntity {
	
	private static final long serialVersionUID = 6385739183421877208L;
	
	@ManyToOne
	@JoinColumn(name = "screening_fk")
	Screening screening;
	
	@ManyToOne
	@JoinColumn(name = "ticket_fk")
	Ticket ticket;
	
	public ScreeningSeat(){}
}
