package com.cinema.web;

import com.cinema.domain.User;
import com.cinema.domain.UserRepository;
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

    @Autowired
    public SignupController(UserRepository userRepository){
        userService = new UserService(userRepository);
    }

    @GetMapping
    public String initCreationForm(@ModelAttribute User user){
        return "signup";
    }

    @PostMapping
    public String processCreationForm(@Valid User user, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return "signup";
        } else {
        	// Save newly created user in database
        	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
    		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userService.save(user);
            redirect.addFlashAttribute("globalMessage", "Successfully signed up");

            // Authenticate registered user
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRolesNames());
            UserRepositoryUserDetailsService userDetailsService = new UserRepositoryUserDetailsService(userService);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            return "redirect:/";
        }
    }
}
