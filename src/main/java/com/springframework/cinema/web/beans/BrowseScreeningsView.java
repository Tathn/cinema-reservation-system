package com.springframework.cinema.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningService;

public class BrowseScreeningsView implements Serializable {

	private static final long serialVersionUID = -4031717695892514485L;
	
	private Collection<Screening> screenings;
	private Collection<Screening> filteredScreenings;

	@Autowired
	private ScreeningService screeningService;

	public BrowseScreeningsView(){}
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		screenings = screeningService.findAllSortByStartsAt();
		filteredScreenings = new ArrayList<Screening>();
	}
	
	public Collection<Screening> getScreenings() { return screenings; }
	public void setScreenings(Collection<Screening> screenings) { this.screenings = screenings; }
	
	public Collection<Screening> getFilteredScreenings() { return filteredScreenings; }
	public void setFilteredScreenings(Collection<Screening> filteredScreenings) { this.filteredScreenings = filteredScreenings; }
	
}
