package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContext;

import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityService;

@Named("loginBean")
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1210614190308165176L;
	private User user = new User();
	private String message;
	
	@Inject
	private UserService userService;
	@Inject
	private SecurityService securityService;
	
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public void logIn() throws IOException{
		if(securityService.authenticate(user, userService)){
			user = new User();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/");
		}
		else{
			FacesContext.getCurrentInstance().addMessage("loginMsg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Invalid username or password.","Try again."));
		}
	}
	
	public void logOut() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("/logout");
	}
}
