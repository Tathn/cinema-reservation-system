package com.springframework.cinema.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Patryk on 2017-04-19.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -1006690702888698974L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@NotNull(message = "Username is required.")
	@Size(min = 4, message = "Username is too short.")
	@Column(name = "username", unique=true, nullable = false)
    private String username;

    @Email(message = "Please provide a valid email address.")
    @NotEmpty(message = "Email is required.")
    @Column(name = "email",unique=true ,nullable = false)
    private String email;

    @NotNull(message = "Password is required.")
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
    
//  @OneToMany(mappedBy = "user")
//	Collection<Ticket> tickets;

    public User(){}
    
    public User(User user){
    	this.setId(user.getId());
    	this.username = user.username;
    	this.email = user.email;
    	this.password = user.password;
    	this.roles = user.roles;
    
    }

    public String getUsername(){ return this.username; }
    public void setUsername(String username){ this.username = username; }

    public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Collection<Role> getRoles() { return roles; }
    public void setRoles(Collection<Role> roles) { this.roles = roles; }
    
    public String[] getRolesNames() { 
    	ArrayList<String> strRoles = new ArrayList<String>();
    	for(Role role : roles){
    		strRoles.add(role.getName());
    	}
    	return strRoles.toArray(new String[strRoles.size()]); 	
    }
    
    public void addRole(Role role) {
       	if(roles == null) {
    		roles = new ArrayList<Role>();
    	}
    	roles.add(role);
    }

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }
}
