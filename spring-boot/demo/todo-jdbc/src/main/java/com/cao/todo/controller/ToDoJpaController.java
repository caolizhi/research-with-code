package com.cao.todo.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cao.todo.domain.ToDo;
import com.cao.todo.domain.ToDoJpa;
import com.cao.todo.domain.ToDoJpaBuilder;
import com.cao.todo.repository.ToDoRepositoryJpa;
import com.cao.todo.validation.ToDoValidationError;
import com.cao.todo.validation.ToDoValidationErrorBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/jpa")
public class ToDoJpaController {

	private ToDoRepositoryJpa toDoRepositoryJpa;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	public ToDoJpaController(ToDoRepositoryJpa toDoRepositoryJpa) {
		this.toDoRepositoryJpa = toDoRepositoryJpa;
	}

	@GetMapping("/todo")
	public ResponseEntity<Iterable<ToDoJpa>> getToDos(){
		return ResponseEntity.ok(toDoRepositoryJpa.findAll());
	}

	@GetMapping("/todo/{id}")
	public ResponseEntity<ToDoJpa> getToDoById(@PathVariable String id){
		Optional<ToDoJpa> toDo = toDoRepositoryJpa.findById(id);
		if(toDo.isPresent()) {
			return ResponseEntity.ok(toDo.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/todo/{id}")
	public ResponseEntity<ToDo> setCompleted(@PathVariable String id){
		Optional<ToDoJpa> toDo = toDoRepositoryJpa.findById(id);
		if(!toDo.isPresent())
			return ResponseEntity.notFound().build();
		ToDoJpa result = toDo.get();
		result.setCompleted(true);
		toDoRepositoryJpa.save(result);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(result.getId()).toUri();
		return ResponseEntity.ok().header("Location",location.toString()).build();
	}

	@RequestMapping(value="/todo", method = {RequestMethod.POST,RequestMethod.PUT})
	public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors){
		ToDoJpa toDoJpa = objectMapper.convertValue(toDo, ToDoJpa.class);
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
		}
		ToDoJpa result = toDoRepositoryJpa.save(toDoJpa);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/todo/{id}")
	public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){
		toDoRepositoryJpa.delete(ToDoJpaBuilder.create().withId(id).build());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/todo")
	public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDoJpa toDo){
		toDoRepositoryJpa.delete(toDo);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ToDoValidationError handleException(Exception exception) {
		return new ToDoValidationError(exception.getMessage());
	}
}
