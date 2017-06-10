package com.springframework.cinema.domain.user;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-04-27.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
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
    	return !(userRepository.findUserByUsername(username) == null);
    }
    
    public boolean checkIfEmailExists(String email) {
    	return !(userRepository.findUserByEmail(email) == null);
    }
}
