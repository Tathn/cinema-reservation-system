package com.springframework.cinema.domain.screening;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.springframework.cinema.infrastructure.model.BaseEntity;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name = "roomSeats")
public class RoomSeat extends BaseEntity {
	
	private static final long serialVersionUID = 6385739183421877208L;
	
	@ManyToOne
	@JoinColumn(name = "room_fk")
	Room room;
	
	public RoomSeat(){}
	
}
