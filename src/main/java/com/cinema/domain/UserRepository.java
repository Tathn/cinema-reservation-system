package com.cinema.domain;

import java.util.Collection;

/**
 * Created by Patryk on 2017-04-19.
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Collection<User> findUsersByRoles(Role role);

}
