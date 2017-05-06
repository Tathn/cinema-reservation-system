package com.cinema.web;

import com.cinema.domain.Customer;
import com.cinema.domain.CustomerRepository;
import com.cinema.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final CustomerService customerService;

    @Autowired
    public SignupController(CustomerRepository customerRepository){
        customerService = new CustomerService(customerRepository);
    }

    @GetMapping
    public String initCreationForm(@ModelAttribute Customer customer){
        return "customers/create";
    }

    @PostMapping
    public String processCreationForm(@Valid Customer customer, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return "customers/create";
        } else {
            customer = customerService.save(customer);
            redirect.addFlashAttribute("globalMessage", "Successfully signed up");

            List<GrantedAuthority> authorities =
                    AuthorityUtils.createAuthorityList("ROLE_USER");
            UserDetails userDetails = new org.springframework.security.core.userdetails
                    .User(customer.getEmail(),customer.getPassword(), authorities);
            Authentication auth =
                    new UsernamePasswordAuthenticationToken(userDetails, customer.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        }
    }
}
