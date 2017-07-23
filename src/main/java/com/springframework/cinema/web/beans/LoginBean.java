package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityService;

@ManagedBean("loginBean")
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1210614190308165176L;
	private User user = new User();
	private String message;
	
	@EJB
	private UserService userService;
	@EJB
	private SecurityService securityService;
	
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public void logIn() throws IOException{
		if(securityService.authenticate(user, userService)){
			user = new User();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/");
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Invalid username or password.","Try again."));
		}
	}
	
	public void logOut() throws IOException{
		SecurityContextHolder.clearContext();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/login");
	}
}
