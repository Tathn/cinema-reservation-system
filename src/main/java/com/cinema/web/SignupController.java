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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Patryk on 2017-05-05.
 */
@Controller
@RequestMapping("/register")
public class SignupController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public SignupController(UserRepository userRepository, RoleRepository roleRepository){
        userService = new UserService(userRepository);
        roleService = new RoleService(roleRepository);
    }

    @GetMapping
    public String initUserCreationForm(@ModelAttribute User user){
        return "signup";
    }

    @PostMapping
    public String processUserCreationForm(@Valid User user, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return "signup";
        } else {
        	Role userRole = roleService.findByName("ROLE_USER");
        	user.addRole(userRole);
        	String encodedPassword = encodePassword(user.getPassword());
        	user.setPassword(encodedPassword);
            user = userService.save(user);
            authenticate(user);
            redirect.addFlashAttribute("globalMessage", "Successfully signed up");
            return "redirect:/";
        }
    }
    
    //TODO refractor
    private void authenticate(User user){
    	List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRolesNames());
        UserRepositoryUserDetailsService userDetailsService = new UserRepositoryUserDetailsService(userService);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
    }
    
    private String encodePassword(String password){
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
    	String encodedPassword = bCryptPasswordEncoder.encode(password);
    	return encodedPassword;
    }
}
