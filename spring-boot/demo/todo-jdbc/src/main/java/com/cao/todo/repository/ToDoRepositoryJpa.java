package com.cao.todo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cao.todo.domain.ToDoJpa;

@Repository
public interface ToDoRepositoryJpa extends CrudRepository<ToDoJpa, String> {

}
