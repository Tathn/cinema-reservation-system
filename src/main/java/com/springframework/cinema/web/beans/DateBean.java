package com.springframework.cinema.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named("dateBean")
@RequestScoped
public class DateBean implements Serializable {

	private static final long serialVersionUID = 6457129120875247157L;
	
	public Date getCurrentDate() { return new Date(); }
}
