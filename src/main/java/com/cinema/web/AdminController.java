package com.cinema.web;

import com.cinema.domain.Role;
import com.cinema.domain.RoleRepository;
import com.cinema.domain.User;
import com.cinema.domain.UserRepository;
import com.cinema.service.RoleService;
import com.cinema.service.UserRepositoryUserDetailsService;
import com.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

/**
 * Created by Patryk on 2017-04-19.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserRepository userRepository, RoleRepository roleRepository){
        userService = new UserService(userRepository);
        roleService = new RoleService(roleRepository);
    }
    
    @GetMapping
    public String getAdminPanel(){
        return "admin/adminPanel";
    }
    
    private Collection<User> populateUsersByRole(String roleName) {
    	Role role = roleService.findByName(roleName);
    	Collection<User> users = userService.findUsersByRoles(role);
    	return users;
    }

    @GetMapping("users")
    public String getUsers(Model model) {
    	Collection<User> users = populateUsersByRole("ROLE_USER");
        model.addAttribute("users",users);
    	return "admin/manageUsers";
    }
    
    @GetMapping("employees")
    public String getEmployees(Model model) {
    	Collection<User> employees = populateUsersByRole("ROLE_EMPLOYEE");
        model.addAttribute("employees",employees);
    	return "admin/manageEmployees";
    }
    
    @GetMapping("employees/create")
    public String initCreateEmployeeForm(@ModelAttribute User user){
    	return "admin/createEmployee";
    }
    
    @PostMapping("employees/create")
    public String processCreateEmployeeForm(@ModelAttribute User user, BindingResult result, RedirectAttributes redirect){
    	if(result.hasErrors()) {
    		return "employees/create";
    	} else {
    		Role userRole = roleService.findByName("ROLE_EMPLOYEE");
        	user.addRole(userRole);
        	String encodedPassword = encodePassword(user.getPassword());
        	user.setPassword(encodedPassword);
            user = userService.save(user);
            redirect.addFlashAttribute("globalMessage", "Successfully created an Employee Account");
    		return "redirect:/admin/employees";
    	}
    }
    
    //TODO refractor
    private String encodePassword(String password){
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
    	String encodedPassword = bCryptPasswordEncoder.encode(password);
    	return encodedPassword;
    }
    
    @GetMapping("delete/{userId}")
    public String removeUser(@PathVariable Long userId, RedirectAttributes redir){
        userService.delete(userId);
        redir.addFlashAttribute("globalMessage","User deleted!");
        return "redirect:/admin/users";
    }
}
