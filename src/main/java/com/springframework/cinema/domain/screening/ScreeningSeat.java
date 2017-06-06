package com.springframework.cinema.domain.screening;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Patryk on 2017-03-06.
 */
@Entity
@Table(name = "screeningSeats")
public class ScreeningSeat implements Serializable {
	
	private static final long serialVersionUID = 6385739183421877208L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name = "screening_fk")
//	Screening screening;
	
//	@ManyToOne
//	@JoinColumn(name = "ticket_fk")
//	Ticket ticket;

	public ScreeningSeat(){}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }
}
