package com.tathn.cinema.domain.user;

import java.util.Collection;

import com.tathn.cinema.infrastructure.model.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2017-04-19.
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Collection<User> findUsersByRoles(Role role);

}
