package com.tathn.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.tathn.cinema.domain.user.Role;
import com.tathn.cinema.domain.user.RoleService;
import com.tathn.cinema.domain.user.User;
import com.tathn.cinema.domain.user.UserService;
import com.tathn.cinema.infrastructure.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

public class SignupView implements Serializable {

	private static final long serialVersionUID = 98650481796588510L;
	
	private User user = new User();
	private String passwordConfirm;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);
	}
	
	public void register() throws IOException{
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
	
	
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public String getPasswordConfirm() { return passwordConfirm; }
	public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
}
