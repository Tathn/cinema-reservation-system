package com.springframework.cinema.domain.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name = "rooms")
public class Room implements Serializable {

	private static final long serialVersionUID = -649613780753687092L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "rows")
	private Integer rows;
	
	@Column(name = "columns")
	private Integer columns;
	
//	@OneToMany(mappedBy="room")
//	Collection<Screening> screenings;
	
//	@OneToMany(mappedBy="room", cascade = CascadeType.ALL, orphanRemoval = true)
//	Collection<RoomSeat> seats;
	
	private Room(){}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Integer getRows() { return rows; }
	public void setRows(Integer rows) { this.rows = rows; }

	public Integer getColumns() { return columns; }
	public void setColumns(Integer columns) { this.columns = columns; }

//	public Collection<Screening> getScreenings() { return screenings; }
//	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }

//	public Collection<RoomSeat> getSeats() { return seats; }
//	public void setSeats(Collection<RoomSeat> seats) { this.seats = seats; }
}
