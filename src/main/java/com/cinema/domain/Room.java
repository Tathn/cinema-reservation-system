package com.cinema.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {

	private static final long serialVersionUID = -649613780753687092L;

	@OneToMany(mappedBy="room")
	Collection<Screening> screenings;
	
	@OneToMany(mappedBy="room")
	Collection<RoomSeat> seats;
	
	public Room(){}
}
