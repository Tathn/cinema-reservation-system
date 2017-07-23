package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.user.Role;
import com.springframework.cinema.domain.user.RoleService;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityService;
import com.springframework.cinema.web.beans.validators.del.UserValidator;

@ManagedBean("registrationBean")
@ViewScoped
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = 1210614190308165176L;
	private	User user = new User();
	private String message;;
	
	@EJB
	private UserService userService;
	
	@EJB
	private RoleService roleService;
	
	@EJB
	private SecurityService securityService;
	
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public void register() throws IOException{
		Role userRole = roleService.findByName("ROLE_USER");
    	user.addRole(userRole);
    	String password = user.getPassword();
    	String encodedPassword = securityService.encodePassword(password);
    	user.setPassword(encodedPassword);
        user = userService.save(user);
        user.setPassword(password);
        securityService.authenticate(user, userService);
        user = new User();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/");
	}
}
