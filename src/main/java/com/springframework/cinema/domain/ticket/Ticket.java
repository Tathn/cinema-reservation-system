package com.springframework.cinema.domain.ticket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.springframework.cinema.domain.screening.Screening;
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
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "isPaid")
	private boolean paid;
	
	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "ticket_user_id_fk"))
	private User user;

	@ManyToOne
	@JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "ticket_screening_id_fk"))
	private Screening screening;
	
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
	private Collection<ScreeningSeat> screeningSeats;
	
	public Ticket(){
		screeningSeats = new ArrayList<ScreeningSeat>();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	
	public float getPrice() { return price; }
	public void setPrice(float price) { this.price = price; }

	public boolean isPaid() { return paid; }
	public void setPaid(boolean paid) { this.paid = paid; }

	public Screening getScreening() { return screening; }
	public void setScreening(Screening screening) { this.screening = screening; }

	public Collection<ScreeningSeat> getScreeningSeats() { return screeningSeats; }
	public void setScreeningSeats(Collection<ScreeningSeat> screeningSeats) { this.screeningSeats = screeningSeats; }

}
