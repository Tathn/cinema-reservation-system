package com.tathn.cinema.web.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.tathn.cinema.domain.user.User;
import com.tathn.cinema.domain.user.UserService;
import com.tathn.cinema.infrastructure.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

public class UpdateUserView implements Serializable {

	private static final long serialVersionUID = -3258204961094221154L;
	
	private String newEmail;
	private String newPassword;
	private String newPasswordConfirm;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void init(){
		FacesContextUtils
        .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
        .getAutowireCapableBeanFactory().autowireBean(this);

	}
	
	public void updateUser() throws IOException{
		User dbRecord = userService.findById(SecurityUtil.getCurrentUser().getId());
    	if(!newEmail.isEmpty())
    		dbRecord.setEmail(newEmail);
    	if(!newPassword.isEmpty()){
    		dbRecord.setPassword(SecurityUtil.encodePassword(newPassword));
    	}
    	userService.save(dbRecord);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Account updated successfully."));
        externalContext.getFlash().setKeepMessages(true);
        externalContext.redirect("/");
	}

	public String getNewEmail() { return newEmail; }
	public void setNewEmail(String newEmail) { this.newEmail = newEmail; }

	public String getNewPassword() { return newPassword; }
	public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

	public String getNewPasswordConfirm() { return newPasswordConfirm; }
	public void setNewPasswordConfirm(String newPasswordConfirm) { this.newPasswordConfirm = newPasswordConfirm; }
	
}
