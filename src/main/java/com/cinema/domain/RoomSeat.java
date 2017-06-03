package com.cinema.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name="seats")
public class Seat extends BaseEntity {
	
	private static final long serialVersionUID = 6385739183421877208L;
	
	@NotEmpty(message = "Name is required")
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotEmpty(message = "Availability is required")
	@Column(name = "available", nullable = false)
	private Boolean available;
	
	//TODO
	//hasOne CinemaRoom
	//hasOne Ticket
	public Seat(){}
	
	public Seat(Seat seat) {
		this.name = seat.name;
		this.available = seat.available;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; } 
	
	public Boolean getAvailable() { return available; }
	public void setAvailable(Boolean available) { this.available = available; }
}
