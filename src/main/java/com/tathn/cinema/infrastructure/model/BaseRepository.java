package com.tathn.cinema.infrastructure.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Created by Patryk on 2017-04-27.
 */
@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends Repository<T,ID> {
    void delete(ID deleted);
    Collection<T> findAll();
    T findOne(ID id);
    T save(T persisted);
}
