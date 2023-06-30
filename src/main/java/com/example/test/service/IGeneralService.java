package com.example.test.service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
//    Iterable<T> findByName(String name);

    void remove(Long id);
}
