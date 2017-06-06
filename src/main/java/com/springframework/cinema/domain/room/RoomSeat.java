package com.springframework.cinema.domain.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name = "roomSeats")
public class RoomSeat implements Serializable {
	
	private static final long serialVersionUID = 6385739183421877208L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "label")
	private String label;
	
	@ManyToOne
	@JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "room_id_fk"))
	Room room;

	private RoomSeat(){}
	
	public RoomSeat(String label, Room room) {
		this.label = label;
		this.room = room;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }

	public Room getRoom() { return room; }
	public void setRoom(Room room) { this.room = room; }
	
}
