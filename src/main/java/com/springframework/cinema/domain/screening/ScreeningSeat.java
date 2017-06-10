package com.springframework.cinema.domain.screening;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomSeat;

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
	
	@Column(name = "label")
	private String label;
	
	@Column(name = "availability")
	private Boolean availability;
	
	@ManyToOne
	@JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "screening_id_fk"))
	private Screening screening;

//	@ManyToOne
//	@JoinColumn(name = "ticket_fk")
//	Ticket ticket;

	private ScreeningSeat(){}
	
	public ScreeningSeat(RoomSeat roomSeat, Screening screening) {
		label = roomSeat.getLabel();
		availability = true;
		this.screening = screening;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public Boolean getAvailability() { return availability;}
	public void setAvailability(Boolean availability) { this.availability = availability; }
}
