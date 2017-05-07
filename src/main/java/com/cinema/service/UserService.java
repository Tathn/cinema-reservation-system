package com.cinema.service;

import com.cinema.domain.User;
import com.cinema.domain.UserRepository;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-04-27.
 */
@Service
public class UserService {
    private final UserRepository customerRepository;

    public UserService(UserRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    public User save(User customer){
        return customerRepository.save(customer);
    }

    public void delete(Long id){
        customerRepository.delete(id);
    }

    public Collection<User> findAll(){
        return customerRepository.findAll();
    }

    public User findById(Long id){
        return customerRepository.findOne(id);
    }
    
    public User findByUsername(String username){
    	return customerRepository.findUserByUsername(username);
    }
}
