package com.restfulwebproject.demo.repository;

import java.util.Optional;

public interface ICrudRepository<T,ID> {

    long count();
    void delete(T entity);
    void deleteAll();
    void delete(Iterable<? extends T> entities);
    void deleteById(ID id);
    boolean existById(ID id);
    Iterable<T> findAll();
    Optional<T> findById(ID id);
    <E extends  T> E save(E entity);
    <E extends T> E saveAll(Iterable<E> entity);
}
