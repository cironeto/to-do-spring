package dev.cironeto.todospring.service;

import java.util.List;

public interface ApplicationCrud<T> {

    T create(T t);

    List<T> findAll();

    T findById(Long id);

    T update(T t);

    void delete(T t);
}
