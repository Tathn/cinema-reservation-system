package com.cinema.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name="tickets")
public class Ticket extends BaseEntity {

	private static final long serialVersionUID = -649613780753687092L;

	@ManyToOne
	@JoinColumn(name = "user_fk")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "screening_fk")
	Screening screening;
	
	@OneToMany(mappedBy = "ticket")
	Collection<ScreeningSeat> screeningSeats;
	
	public Ticket(){}

}
