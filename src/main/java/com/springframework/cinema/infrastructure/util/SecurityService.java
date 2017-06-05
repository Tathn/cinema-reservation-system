package com.springframework.cinema.infrastructure.util;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserRepositoryUserDetailsService;
import com.springframework.cinema.domain.user.UserService;

@Service
public class SecurityService {

	public String encodePassword(String password){
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
    	String encodedPassword = bCryptPasswordEncoder.encode(password);
    	return encodedPassword;
    }
	
	public void authenticate(User user, UserService userService){
    	List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRolesNames());
        UserRepositoryUserDetailsService userDetailsService = new UserRepositoryUserDetailsService(userService);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
    }
}
