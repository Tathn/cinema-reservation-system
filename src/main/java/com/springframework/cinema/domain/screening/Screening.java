package com.springframework.cinema.domain.screening;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Patryk on 2017-06-03.
 */
@Entity
@Table(name="screenings")
public class Screening implements Serializable {

	private static final long serialVersionUID = 3510289568546131848L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name = "movie_fk")
//	Movie movie;
//	
//	@ManyToOne
//	@JoinColumn(name = "room_fk")
//	Room room;
	
//	@OneToMany(mappedBy = "screening")
//	Collection<Ticket> tickets;
	
//	@OneToMany(mappedBy = "screening")
//	Collection<ScreeningSeat> seats;

	public Long getId() { return id; }


	public void setId(Long id) { this.id = id; }

}
