package com.springframework.cinema.domain.user;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-04-19.
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Collection<User> findUsersByRoles(Role role);

}
