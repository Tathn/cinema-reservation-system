package com.cinema.domain;

/**
 * Created by Patryk on 2017-04-19.
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);

}
