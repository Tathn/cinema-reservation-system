package com.springframework.cinema.domain.user;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.springframework.cinema.infrastructure.model.BaseEntity;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

	private static final long serialVersionUID = -2405515523376698569L;
	
	@NotEmpty(message = "Role is required")
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<User> users;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

}
