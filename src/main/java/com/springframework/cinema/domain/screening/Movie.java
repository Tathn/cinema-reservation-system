package com.springframework.cinema.domain.screening;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.springframework.cinema.infrastructure.model.BaseEntity;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {

	private static final long serialVersionUID = 369022311879378956L;
	
	@NotEmpty(message = "Title is required")
	@Column(name = "title", nullable = false)
	private String title;
	
	@NotEmpty(message = "Genre is required")
	@Column(name = "genre", nullable = false)
	private String genre;
	
	@NotNull(message = "Duration is required")
	@Column(name = "duration", nullable = false)
	private Integer duration;

	@NotEmpty(message = "Description is required")
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "available", nullable = false)
	private Boolean available;

	@OneToMany(mappedBy="movie")
	Collection<Screening> screenings;
	
	
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

	public Integer getDuration() { return duration; }
	public void setDuration(Integer duration) { this.duration = duration; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public Boolean getAvailable() { return available; }
	public void setAvailable(Boolean available) { this.available = available; }
}
