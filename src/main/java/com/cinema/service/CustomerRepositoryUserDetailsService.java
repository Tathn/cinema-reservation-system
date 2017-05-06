package com.cinema.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cinema.domain.Customer;

@Service
public class CustomerRepositoryUserDetailsService implements UserDetailsService {
	private final CustomerService customerService;
	
	@Autowired
	public CustomerRepositoryUserDetailsService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		Customer customer = customerService.findByUsername(username);
		if(customer == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new CustomUserDetails(customer);
	}
	
	private final class CustomUserDetails extends Customer implements UserDetails {

		private static final long serialVersionUID = -8006025957151631338L;

		private CustomUserDetails(Customer customer) {
	        super(customer);
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return AuthorityUtils.createAuthorityList("ROLE_USER");
	    }

	    @Override
	    public String getUsername() {
	        return super.getUsername();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	    
	}

}