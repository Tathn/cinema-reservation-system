package com.cinema.domain;

/**
 * Created by Patryk on 2017-04-19.
 */
public interface CustomerRepository extends BaseRepository<Customer, Long> {
    Customer findCustomerByUsername(String username);
    Customer findCustomerByEmail(String email);

}
