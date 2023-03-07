package com.backend.todo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{
	
	public List<Todo> findAllByOrderByIdDesc();
}
