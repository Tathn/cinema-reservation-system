package com.springframework.cinema.domain.movie;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-05-27.
 */
@Entity
@Table(name = "movies")
public class Movie implements Serializable {

	private static final long serialVersionUID = 369022311879378956L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

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
	@Size(max = 1024, message = "Description is too long")
	@Column(name = "description", nullable = false, length = 1024)
	private String description;
	
	@Column(name = "available", nullable = false)
	private Boolean available;
	
	@Column(name = "age_restriction", nullable = false)
	private int ageRestriction;
	
	@Column(name = "two_dimensional", nullable = false)
	private Boolean twoDimensional;
	
	@Column(name = "three_dimensional", nullable = false)
	private Boolean threeDimensional;
	
	@Column(name = "voice_over", nullable = false)
	private Boolean voiceOver;
	
	@Column(name = "dubbing", nullable = false)
	private Boolean dubbing;
	
	@Column(name = "subtitles", nullable = false)
	private Boolean subtitles;
	
//	@OneToMany(mappedBy="movie")
//	Collection<Screening> screenings;	
	
	public Movie(){
		available = twoDimensional = threeDimensional = voiceOver = dubbing = subtitles = false;
	}
	
	public Movie(Movie movie) {
		this.title = movie.title;
		this.genre = movie.genre;
		this.duration = movie.duration;
		this.description = movie.description;
		this.available = movie.available;
		this.ageRestriction = movie.ageRestriction;
		this.twoDimensional = movie.twoDimensional;
		this.threeDimensional = movie.threeDimensional;
		this.voiceOver = movie.voiceOver;
		this.dubbing = movie.dubbing;
		this.subtitles = movie.subtitles;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
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
	
	public int getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(int ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	public Boolean getTwoDimensional() {
		return twoDimensional;
	}

	public void setTwoDimensional(Boolean twoDimensional) {
		this.twoDimensional = twoDimensional;
	}

	public Boolean getThreeDimensional() {
		return threeDimensional;
	}

	public void setThreeDimensional(Boolean threeDimensional) {
		this.threeDimensional = threeDimensional;
	}

	public Boolean getVoiceOver() {
		return voiceOver;
	}

	public void setVoiceOver(Boolean voiceOver) {
		this.voiceOver = voiceOver;
	}

	public Boolean getDubbing() {
		return dubbing;
	}

	public void setDubbing(Boolean dubbing) {
		this.dubbing = dubbing;
	}

	public Boolean getSubtitles() {
		return subtitles;
	}

	public void setSubtitles(Boolean subtitles) {
		this.subtitles = subtitles;
	}

	@Override
	public String toString() { return title; }
	
	@Override
	public boolean equals(Object obj) { return Objects.equals(obj, this.getTitle()); }
}
