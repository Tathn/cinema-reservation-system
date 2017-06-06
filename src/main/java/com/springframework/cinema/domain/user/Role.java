package com.springframework.cinema.domain.user;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = -2405515523376698569L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@NotEmpty(message = "Role is required")
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<User> users;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

}
