package com.springframework.cinema.domain.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springframework.cinema.domain.ticket.TicketService;

/**
 * Created by Patryk on 2017-04-27.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TicketService ticketService;
    
    @Autowired
    public UserService(UserRepository userRepository,TicketService ticketService){
        this.userRepository = userRepository;
        this.ticketService = ticketService;
    }


    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
    	ticketService.deleteByUserId(id);
        userRepository.delete(id);
    }

    public Collection<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findOne(id);
    }
    
    public User findByUsername(String username){
    	return userRepository.findUserByUsername(username);
    }
    
    public Collection<User> findUsersByRoles(Role role){
    	return userRepository.findUsersByRoles(role);
    }
    
    public boolean checkIfUsernameExists(String username) {
    	//TODO Rewrite to custom query looking only for name and not for entire object
    	return userRepository.findUserByUsername(username) != null;
    }
    
    public boolean checkIfEmailExists(String email) {
    	//TODO Rewrite to custom query looking only for name and not for entire object
    	return userRepository.findUserByEmail(email) != null;
    }
}
