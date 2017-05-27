package com.cinema.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name="movies")
public class Movie extends BaseEntity {

	private static final long serialVersionUID = 369022311879378956L;
	
	@NotEmpty(message = "Title is required")
	@Column(name = "title", nullable = false)
	private String title;
	
	@NotEmpty(message = "Genre is required")
	@Column(name = "genre", nullable = false)
	private String genre;
	
	@NotEmpty(message = "Duration is required")
	@Column(name = "duration", nullable = false)
	private Short duration;

	@NotEmpty(message = "Description is required")
	@Column(name = "description", nullable = false)
	private String description;
	
	@NotEmpty(message = "Availability is required")
	@Column(name = "available", nullable = false)
	private Boolean available;

	//TODO
	//hasMany cinemaRooms
	//hasMany tickets
	
	
	public Movie(){}
	
	public Movie(Movie movie) {
		this.title = movie.title;
		this.genre = movie.genre;
		this.duration = movie.duration;
		this.description = movie.description;
		this.available = movie.available;
	}
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getGenre() { return genre; }
	public void setGenre(String genre) { this.genre = genre; }

	public Short getDuration() { return duration; }
	public void setDuration(Short duration) { this.duration = duration; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public Boolean getAvailable() { return available; }
	public void setAvailable(Boolean available) { this.available = available; }
}
