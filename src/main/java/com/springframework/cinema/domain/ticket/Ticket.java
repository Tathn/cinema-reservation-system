package com.springframework.cinema.domain.ticket;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.springframework.cinema.domain.screening.ScreeningSeat;
import com.springframework.cinema.domain.user.User;


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
	
	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "ticket_user_id_fk"))
	private User user;

    @OneToMany
	private Collection<ScreeningSeat> screeningSeats;
	
	private Ticket(){}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Collection<ScreeningSeat> getScreeningSeats() { return screeningSeats; }
	public void setScreeningSeats(Collection<ScreeningSeat> screeningSeats) { this.screeningSeats = screeningSeats; }

}
