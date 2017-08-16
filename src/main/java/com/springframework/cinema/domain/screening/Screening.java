package com.springframework.cinema.domain.screening;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.infrastructure.util.DateUtil;

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
	
	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;
	
	@Column(name = "screeningStart")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date startsAt;
	
	@Column(name = "screeningFinish")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date finishesAt;
	
	@Column(name = "movie_attributes")
	private String movieAttributes;
	
	@Column(name = "adult_ticket_price")
	private float adultTicketPrice;
	
	@Column(name = "child_ticket_price")
	private float childTicketPrice;
	
	@ManyToOne
	@NotNull(message = "Movie must be specified")
	@JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "movie_id_fk"))
	private Movie movie;
	
	@ManyToOne
	@NotNull(message = "Room must be specified")
	@JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "screening_room_id_fk"))
	private Room room;
	
//	@OneToMany(mappedBy = "screening")
//	Collection<Ticket> tickets;

	public Screening(){
		date = finishesAt = startsAt = new Date();
		movie = new Movie();
		room = new Room();
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }
	
	public Date getStartsAt() { return DateUtil.setDateTime(getDate(), startsAt); }
	public void setStartsAt(Date startsAt) { this.startsAt = startsAt; }
	
	public Date getFinishesAt() { return finishesAt; }
	public void setFinishesAt(Date finishesAt) { this.finishesAt = finishesAt; }
	
	public Movie getMovie() { return movie; }
	public void setMovie(Movie movie) { this.movie = movie; }
	
	public Room getRoom() { return room; }
	public void setRoom(Room room) { this.room = room; }

	public String getMovieAttributes() {
		return movieAttributes;
	}

	public void setMovieAttributes(String movieAttributes) {
		this.movieAttributes = movieAttributes;
	}

	public float getAdultTicketPrice() {
		return adultTicketPrice;
	}

	public void setAdultTicketPrice(float adultTicketPrice) {
		this.adultTicketPrice = adultTicketPrice;
	}

	public float getChildTicketPrice() {
		return childTicketPrice;
	}

	public void setChildTicketPrice(float childTicketPrice) {
		this.childTicketPrice = childTicketPrice;
	}

	@Override
	public String toString() {
		return "Id: " + id + " \nDate " + date + " \n" + "StartsAt: " + getStartsAt() + " \n" + "FinishesAt: " + finishesAt + " \n" + "Movie: " + movie + " \n" + "Room: " + room; 
	}
	
	@Override
	public boolean equals(Object obj) {
	    return (obj != null && getClass() == obj.getClass() && id != null)
	        ? id.equals(((Screening) obj).getId())
	        : (obj == this);
	}

	@Override
	public int hashCode() {
	    return (id != null) 
	        ? (getClass().hashCode() + id.hashCode())
	        : super.hashCode();
	}
}
