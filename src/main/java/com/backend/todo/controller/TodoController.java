package com.backend.todo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.todo.model.Todo;
import com.backend.todo.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService service;

	@PostMapping
	public ResponseEntity<Todo> saveTodo(@RequestBody Todo todo) {
		return new ResponseEntity<>(service.saveTodo(todo), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(service.getTodoList(), HttpStatus.OK);
	}

	@PutMapping("/{todoId}")
	public ResponseEntity<?> updateTodoByField(@PathVariable("todoId") Integer id, @RequestBody Map<String, Object> fields) {
		Todo todo = service.updateTodoByFields(id, fields);
		if(todo != null) {
			return new ResponseEntity<>(todo, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}

	@GetMapping("/{todoId}")
	public ResponseEntity<?> getTodoById(@PathVariable("todoId") Integer id) {
		Optional<Todo> todoOptional = service.getTodoById(id);
		if (todoOptional.isPresent()) {
			return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{todoId}")
	public ResponseEntity<?> deleteTodoById(@PathVariable("todoId") Integer id) {
		
		Optional<Todo> todoOptional = service.getTodoById(id);
		if (todoOptional.isPresent()) {
			service.deleteTodoById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
