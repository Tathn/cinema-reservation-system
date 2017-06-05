package com.springframework.cinema.domain.user;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-05-09.
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    Collection<Role> findRolesByUsersUsername(String username);
    Role findRoleByName(String name);

}
