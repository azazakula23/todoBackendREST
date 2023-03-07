package com.backend.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
	
	@Id
	@GeneratedValue
	private int id;
	private String task;
	private boolean completed;
	
	public Todo(String task, boolean completed) {
		this.task = task ;
		this.completed = completed;
	}

}
