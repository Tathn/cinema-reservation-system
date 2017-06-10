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

import org.springframework.format.annotation.DateTimeFormat;

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.room.Room;

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
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
	private Date startsAt;
	
	@Column(name = "screeningFinish")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
	private Date finishesAt;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "movie_id_fk"))
	Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "screening_room_id_fk"))
	Room room;
	
//	@OneToMany(mappedBy = "screening")
//	Collection<Ticket> tickets;

	private Screening(){}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }

	public Date getStartsAt() { return startsAt; }
	public void setStartsAt(Date startsAt) { this.startsAt = startsAt; }
	
	public Date getFinishesAt() { return finishesAt; }
	public void setFinishesAt(Date finishesAt) { this.finishesAt = finishesAt; }
	
	public Movie getMovie() { return movie; }
	public void setMovie(Movie movie) { this.movie = movie; }
	
	public Room getRoom() { return room; }
	public void setRoom(Room room) { this.room = room; }

}
