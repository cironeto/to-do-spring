package dev.cironeto.todospring.service;

import java.util.List;

public interface ApplicationCrudService<REQ,RES> {

    RES create(REQ req);

    List<RES> findAll();

    RES findById(Long id);

    RES update(REQ t, Long id);

    void delete(Long id);
}
