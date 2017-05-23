package com.cinema.domain;

import java.util.Collection;

import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2017-05-09.
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    Collection<Role> findRolesByUsersUsername(String username);
    Role findRoleByName(String name);

}
