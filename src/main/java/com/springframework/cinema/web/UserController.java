package com.springframework.cinema.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springframework.cinema.domain.user.Role;
import com.springframework.cinema.domain.user.RoleRepository;
import com.springframework.cinema.domain.user.RoleService;
import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserRepository;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.domain.user.UserValidator;
import com.springframework.cinema.infrastructure.util.SecurityService;

@Controller
public class UserController {
	
	private static final String VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM= "user/employee/createOrUpdateEmployeeForm";
	private static final String VIEWS_USER_UPDATE_FORM= "user/updateUserForm";
	
	private final UserService userService;
	private final UserValidator userValidator;
    private final RoleService roleService;
    private final SecurityService securityService;
    
    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository){
        userService = new UserService(userRepository);
        userValidator = new UserValidator(userRepository);
        roleService = new RoleService(roleRepository);
        securityService = new SecurityService();
    }
    
    @InitBinder("user")
    public void initUserBinder(WebDataBinder dataBinder) {
    	dataBinder.setValidator(userValidator);
    }
    
    // Anonymous
    @GetMapping("/register")
    public String initUserCreationForm(@ModelAttribute User user){
        return "user/signupUserForm";
    }

    @PostMapping("/register")
    public String processUserCreationForm(@Valid User user, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return "user/signupUserForm";
        } else {
        	Role userRole = roleService.findByName("ROLE_USER");
        	user.addRole(userRole);
        	String encodedPassword = securityService.encodePassword(user.getPassword());
        	user.setPassword(encodedPassword);
            user = userService.save(user);
            securityService.authenticate(user, userService);
            redirect.addFlashAttribute("globalMessage", "Successfully signed up");
            return "redirect:/";
        }
    }

	@GetMapping("/login")
    public String initLoginForm(){
        return "user/loginUserForm";
    }
	
	// Logged User
    @GetMapping("/account")
    public String getManageAccountPanel(){
        return "user/userPanel";
    }

    @GetMapping("/account/edit")
    public String initEditAccountForm(Model model){
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return VIEWS_USER_UPDATE_FORM;
    }

    @PostMapping("/account/edit")
    public String processEditAccountForm(@ModelAttribute User user, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return VIEWS_USER_UPDATE_FORM;
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
    
    // EMPLOYEE
    
    @GetMapping("/employee")
    public String getEmployeePanelView() {
    	return "user/employee/employeePanel";
    }
    
    // ADMIN
    @GetMapping("/admin")
    public String getAdminPanel(){
        return "user/admin/adminPanel";
    }
    
    private Collection<User> populateUsersByRole(String roleName) {
    	Role role = roleService.findByName(roleName);
    	Collection<User> users = userService.findUsersByRoles(role);
    	return users;
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
    	Collection<User> users = populateUsersByRole("ROLE_USER");
        model.addAttribute("users",users);
    	return "user/manageUsers";
    }
    
    @GetMapping("/admin/users/employees")
    public String getEmployees(Model model) {
    	Collection<User> employees = populateUsersByRole("ROLE_EMPLOYEE");
        model.addAttribute("employees",employees);
    	return "user/employee/manageEmployees";
    }
    
    @GetMapping("/admin/users/employees/create")
    public String initCreateEmployeeForm(@ModelAttribute User user){
    	return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/admin/users/employees/create")
    public String processCreateEmployeeForm(@ModelAttribute User user, BindingResult result, RedirectAttributes redirect){
    	if(result.hasErrors()) {
    		return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
    	} else {
    		Collection<Role> userRoles = new ArrayList<Role>();
    		userRoles.add(roleService.findByName("ROLE_EMPLOYEE"));
    		userRoles.add(roleService.findByName("ROLE_USER"));
        	user.setRoles(userRoles);
        	String encodedPassword = securityService.encodePassword(user.getPassword());
        	user.setPassword(encodedPassword);
            user = userService.save(user);
            redirect.addFlashAttribute("globalMessage", "Successfully created an Employee Account");
    		return "redirect:/admin/users/employees";
    	}
    }
    
    @PostMapping("/admin/users/delete/{userId}")
    public String removeUser(@PathVariable Long userId, RedirectAttributes redir){
        userService.delete(userId);
        redir.addFlashAttribute("globalMessage","User removed successfully!");
        return "redirect:/admin/users";
    }
}
