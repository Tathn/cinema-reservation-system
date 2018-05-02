package com.tathn.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.tathn.cinema.domain.user.Role;
import com.tathn.cinema.domain.user.RoleService;
import com.tathn.cinema.domain.user.User;
import com.tathn.cinema.domain.user.UserService;
import com.tathn.cinema.infrastructure.util.SecurityUtil;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

public class ManageUsersView implements Serializable {

	private static final long serialVersionUID = 8319987811331104086L;

	private User user = new User();
	private Collection<User> users = new ArrayList<User>();
	private Collection<User> filteredUsers = new ArrayList<User>();
	private Collection<Role> roles = new ArrayList<Role>();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		users = userService.findAll();
		roles = roleService.findAll();
	}
	
	
	public void reloadUsers(){
		users = userService.findAll();
	}
	
	public void deleteUser(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long userId = Long.parseLong((params.get("user_id")));
		userService.delete(userId);
		reloadUsers();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully."));
	}
	
	public void createEmployee() throws IOException{
		Collection<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleService.findByName("ROLE_EMPLOYEE"));
		userRoles.add(roleService.findByName("ROLE_USER"));
    	user.setRoles(userRoles);
    	user.setEmail(user.getUsername() + "@cinema.com");
    	user.setPassword(SecurityUtil.encodePassword(user.getPassword()));
        user = userService.save(user);
        user = new User();
        reloadUsers();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employee created successfully."));
		RequestContext.getCurrentInstance().execute("PF('createEmployeeDialog').hide()");
	}

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Collection<User> getUsers() { return users; }
	public void setUsers(Collection<User> users) { this.users = users; }
	
	public Collection<User> getFilteredUsers() { return filteredUsers; }
	public void setFilteredUsers(Collection<User> filteredUsers) { this.filteredUsers = filteredUsers; }
	
	public Collection<Role> getRoles() { return roles; }
	public void setRoles(Collection<Role> roles) { this.roles = roles; }
}
