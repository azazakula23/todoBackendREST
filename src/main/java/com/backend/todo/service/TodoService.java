package com.backend.todo.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.backend.todo.dao.TodoRepository;
import com.backend.todo.model.Todo;

@Service
public class TodoService {

	@Autowired
	private TodoRepository repository;

	public Todo saveTodo(Todo todo) {
		return repository.save(todo);
	}

	public Optional<Todo> getTodoById(int id) {
		return repository.findById(id);
	}

	public List<Todo> getTodoList() {
		return repository.findAllByOrderByIdDesc();
	}

	public Todo updateTodoByFields(int id, Map<String, Object> fields) {
		Optional<Todo> existingTodo = repository.findById(id);

		if (existingTodo.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Todo.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, existingTodo.get(), value);
			});

			return repository.save(existingTodo.get());
		}

		return null;
	}

	public void deleteTodoById(int todoId) {
		repository.deleteById(todoId);
	}

}
