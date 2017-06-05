package com.springframework.cinema.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserRepository;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.SecurityService;

/**
 * Created by Patryk on 2017-05-01.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final String VIEWS_USER_CREATE_OR_UPDATE_FORM= "user/createOrUpdateUserForm";
	
	
	private final UserService userService;
	private final SecurityService securityService;
	
    @Autowired
    public AccountController(UserRepository userRepository){
        userService = new UserService(userRepository);
        securityService = new SecurityService();
    }

    @GetMapping
    public String getManageAccountPanel(){
        return "user/userPanel";
    }

    @GetMapping("edit")
    public String initEditAccountForm(Model model){
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return VIEWS_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("edit")
    public String processEditAccountForm(@ModelAttribute User user, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return VIEWS_USER_CREATE_OR_UPDATE_FORM;
        } else {
        	// Get database record of edited user
        	User dbRecord = userService.findById(user.getId());
        	dbRecord.setUsername(user.getUsername());
        	dbRecord.setEmail(user.getEmail());
        	String userPassword = user.getPassword();
        	if(userPassword != ""){
        		String encodedPassword = securityService.encodePassword(userPassword);
        		dbRecord.setPassword(encodedPassword);
        	}
            userService.save(dbRecord);
            securityService.authenticate(dbRecord,userService);
            redir.addFlashAttribute("globalMessage","Account details saved!");
            return "redirect:/account";
        }
    }
}
