package com.tathn.cinema.domain.room;

import java.io.Serializable;
import java.util.Objects;

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
	
	public Room(){
		name = "Undefined";
		rows = 0;
		columns = 0;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Integer getRows() { return rows; }
	public void setRows(Integer rows) { this.rows = rows; }

	public Integer getColumns() { return columns; }
	public void setColumns(Integer columns) { this.columns = columns; }

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return Objects.equals(obj, this.getName());
	}
	
//	public Collection<Screening> getScreenings() { return screenings; }
//	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }

//	public Collection<RoomSeat> getSeats() { return seats; }
//	public void setSeats(Collection<RoomSeat> seats) { this.seats = seats; }
}
