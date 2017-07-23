package com.springframework.cinema.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;

import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningService;

@ManagedBean("screeningBean")
@ViewScoped
public class ScreeningBean implements Serializable {

	private static final long serialVersionUID = -3210711807003424547L;
	
	private Collection<Screening> screenings = new ArrayList<Screening>();
	private Collection<Screening> filteredScreenings = new ArrayList<Screening>();
	
	@EJB
	private ScreeningService screeningService;
	
	public ScreeningBean(){}
	
	@PostConstruct
	public void init(){
		screenings = screeningService.findAll();
	}

	public Collection<Screening> getScreenings() { return screenings; }
	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }

	public Collection<Screening> getFilteredScreenings() {
		return filteredScreenings;
	}
	
	public void setFilteredScreenings(Collection<Screening> filteredScreenings) {
		this.filteredScreenings = filteredScreenings;
	}
	
	public void setScreeningService(ScreeningService screeningService) {
		this.screeningService = screeningService;
	}

}
