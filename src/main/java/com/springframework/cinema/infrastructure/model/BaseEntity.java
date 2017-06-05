package com.springframework.cinema.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Patryk on 2017-04-28.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 8446522415730561872L;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
