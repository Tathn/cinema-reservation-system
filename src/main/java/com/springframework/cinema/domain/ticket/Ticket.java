package com.springframework.cinema.domain.ticket;

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
@Table(name="tickets")
public class Ticket implements Serializable {

	private static final long serialVersionUID = -649613780753687092L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name = "user_fk")
//	User user;
	
//	@ManyToOne
//	@JoinColumn(name = "screening_fk")
//	Screening screening;
	
//	@OneToMany(mappedBy = "ticket")
//	Collection<ScreeningSeat> screeningSeats;
	
	private Ticket(){}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

}
