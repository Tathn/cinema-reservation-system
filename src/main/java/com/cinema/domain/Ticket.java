package com.cinema.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name="tickets")
public class Ticket extends BaseEntity {

	private static final long serialVersionUID = -649613780753687092L;

	
	//TODO
	//hasOne Room
	//hasOne User
	public Ticket(){}
	
	public Ticket(Ticket ticket) {
	
	}

}
