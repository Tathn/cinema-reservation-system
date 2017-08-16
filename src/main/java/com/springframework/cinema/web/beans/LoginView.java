package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.jsf.FacesContextUtils;
import org.springframework.web.servlet.support.RequestContext;

import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityUtil;

public class LoginView implements Serializable {

	private static final long serialVersionUID = 1210614190308165176L;
	private User user = new User();
	
	@Autowired
	private UserService userService;

	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
	}

	public void logIn() throws IOException{
		if(SecurityUtil.authenticate(user, userService)){
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
	
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
}
