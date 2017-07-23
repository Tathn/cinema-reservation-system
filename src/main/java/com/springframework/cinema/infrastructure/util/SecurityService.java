package com.springframework.cinema.infrastructure.util;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springframework.cinema.domain.user.User;
import com.springframework.cinema.domain.user.UserRepositoryUserDetailsService;
import com.springframework.cinema.domain.user.UserService;

@Service
public class SecurityService {
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public String encodePassword(String password){ 
    	String encodedPassword = bCryptPasswordEncoder.encode(password);
    	return encodedPassword;
    }
	
	public boolean authenticate(User user, UserService userService){
		
		UserRepositoryUserDetailsService userDetailsService = new UserRepositoryUserDetailsService(userService);
		try{
			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
			if(bCryptPasswordEncoder.matches(user.getPassword(), userDetails.getPassword())){
	        	Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
	        	SecurityContextHolder.getContext().setAuthentication(auth);
	        	return true;
	        }
	        else return false;
		}
		catch(UsernameNotFoundException e){
			return false;
		}
//        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRolesNames());
//        UserRepositoryUserDetailsService userDetailsService = new UserRepositoryUserDetailsService(userService);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//        if(userDetails.getPassword() == user.getPassword()){
//        	Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }    
    }
	
//	public boolean authenticated(boolean authStatus){
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		return authentication.isAuthenticated() == authStatus;
//	}
}
