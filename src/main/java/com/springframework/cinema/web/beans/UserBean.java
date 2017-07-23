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
import com.springframework.cinema.domain.user.Role;
import com.springframework.cinema.domain.user.RoleService;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityService;

@ManagedBean("userBean")
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 8319987811331104086L;

	private	User user = new User();
	private Collection<User> users = new ArrayList<User>();
	private Collection<User> filteredUsers = new ArrayList<User>();
	private Collection<Role> roles = new ArrayList<Role>();
	
	@EJB
	private UserService userService;
	
	@EJB
	private RoleService roleService;
	
	@EJB
	private SecurityService securityService;
	
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Collection<User> getUsers() { return users; }
	public void setUsers(Collection<User> users) { this.users = users; }
	
	public Collection<User> getFilteredUsers() { return filteredUsers; }
	public void setFilteredUsers(Collection<User> filteredUsers) { this.filteredUsers = filteredUsers; }
	
	public Collection<Role> getRoles() { return roles; }
	public void setRoles(Collection<Role> roles) { this.roles = roles; }
	
	@PostConstruct
	public void init(){
		users = userService.findAll();
		roles = roleService.findAll();
	}
	
//	public void update() throws IOException{
//		User dbRecord = userService.findById(user.getId());
//    	dbRecord.setUsername(user.getUsername());
//    	dbRecord.setEmail(user.getEmail());
//    	String userPassword = user.getPassword();
//    	if(userPassword != ""){
//    		String encodedPassword = securityService.encodePassword(userPassword);
//    		dbRecord.setPassword(encodedPassword);
//    	}
//        userService.save(dbRecord);
//        securityService.authenticate(dbRecord,userService);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("/");
//	}
}
