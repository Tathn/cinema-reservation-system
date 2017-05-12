package com.cinema.web;

import com.cinema.domain.Role;
import com.cinema.domain.RoleRepository;
import com.cinema.domain.User;
import com.cinema.domain.UserRepository;
import com.cinema.service.RoleService;
import com.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

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

    @GetMapping("users")
    public String getUsers(Model model){
    	Role role = roleService.findByName("ROLE_USER");
    	Collection<User> users = userService.findUsersByRoles(role);
        model.addAttribute("users",users);
    	return "admin/manageUsers";
    }
    
    @GetMapping("/admin/edit/{userId}")
    public String initEditForm(@PathVariable Long userId, Model model){

        model.addAttribute("user", userService.findById(userId));
        return "admin/edit";
    }

    @PostMapping("/admin/edit/{userId}")
    public String processEditForm(@PathVariable Long userId, @ModelAttribute User user, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return "admin/edit";
        } else {
            userService.save(user);
            redir.addFlashAttribute("message","User edited!");
            return "redirect:/users/";
        }
    }

    @GetMapping("/admin/delete/{userId}")
    public String removeCustomer(@PathVariable Long userId, RedirectAttributes redir){
        userService.delete(userId);
        redir.addFlashAttribute("message","User deleted!");
        return "redirect:/users/";
    }
}
