package com.springframework.cinema.domain.user;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
	
	private final UserService userService;
	
	@Autowired
	public UserRepositoryUserDetailsService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new CustomUserDetails(user);
	}
	
	private final class CustomUserDetails extends User implements UserDetails {

		private static final long serialVersionUID = -8006025957151631338L;

		private CustomUserDetails(User user) {
	        super(user);
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	    	return AuthorityUtils.createAuthorityList(getRolesNames());
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