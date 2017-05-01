package com.cinema.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

import java.util.Collection;

/**
 * Created by Patryk on 2017-04-19.
 */
public interface CustomerRepository extends BaseRepository<Customer, Long> {
    Customer findByName(String name);


}
