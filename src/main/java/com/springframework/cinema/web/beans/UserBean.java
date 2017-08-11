package com.springframework.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.springframework.cinema.domain.user.Role;
import com.springframework.cinema.domain.user.RoleService;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityUtil;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 8319987811331104086L;

	private	User user = new User();
	private Collection<User> users = new ArrayList<User>();
	private Collection<User> filteredUsers = new ArrayList<User>();
	private Collection<Role> roles = new ArrayList<Role>();
	
	@Inject
	private UserService userService;
	
	@Inject
	private RoleService roleService;
	
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
		System.out.println("UserBean is contructed");
		users = userService.findAll();
		roles = roleService.findAll();
	}
	
	@PreDestroy
	public void dest(){
		System.out.println("UserBean is destroyed");
	}
	
	public void registerUser() throws IOException{
		Role userRole = roleService.findByName("ROLE_USER");
    	user.addRole(userRole);
    	String password = user.getPassword();
    	String encodedPassword = SecurityUtil.encodePassword(password);
    	user.setPassword(encodedPassword);
        user = userService.save(user);
        user.setPassword(password);
        SecurityUtil.authenticate(user, userService);
        user = new User();
        init();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Account created successfully."));
        externalContext.getFlash().setKeepMessages(true);
        externalContext.redirect("/");
	}
	
	public void registerEmployee() throws IOException{
		Collection<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleService.findByName("ROLE_EMPLOYEE"));
		userRoles.add(roleService.findByName("ROLE_USER"));
    	user.setRoles(userRoles);
    	user.setEmail(user.getUsername() + "@cinema.com");
    	String password = user.getPassword();
    	String encodedPassword = SecurityUtil.encodePassword(password);
    	user.setPassword(encodedPassword);
        user = userService.save(user);
        user = new User();
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employee created successfully."));
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("/admin/users");
        externalContext.getFlash().setKeepMessages(true);
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
