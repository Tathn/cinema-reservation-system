package com.tathn.cinema.infrastructure.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tathn.cinema.domain.user.User;
import com.tathn.cinema.domain.user.UserRepositoryUserDetailsService;
import com.tathn.cinema.domain.user.UserService;

public class SecurityUtil {
	
	private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public static String encodePassword(String password){
		return bCryptPasswordEncoder.encode(password);
    }
	
	public static boolean authenticate(User user, UserService userService){
		
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
    }
	
	public static boolean isAuthenticated(){
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
	
	public static User getCurrentUser(){
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
