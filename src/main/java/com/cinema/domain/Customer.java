package com.cinema.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Patryk on 2017-04-19.
 */
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {


    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    public Customer(){}

    public String getName(){
        return this.name;
    }
    public void setName(String name){ this.name = name; }

    public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email; }

}
