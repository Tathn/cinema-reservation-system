package com.tathn.cinema.domain.user;

import java.util.Collection;

import org.springframework.stereotype.Service;

/**
 * Created by Patryk on 2017-05-09.
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    public Role save(Role role){
        return roleRepository.save(role);
    }

    public void delete(Long id){
        roleRepository.delete(id);
    }

    public Collection<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findById(Long id){
        return roleRepository.findOne(id);
    }
    
    public Role findByName(String name){
    	return roleRepository.findRoleByName(name);
    }
}
