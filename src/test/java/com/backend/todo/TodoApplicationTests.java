package com.backend.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.backend.todo.model.Todo;
import com.backend.todo.model.TodoList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class TodoApplicationTests {

	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";
	
	private String formattingString = "%s:%s/todo";

	private static RestTemplate resttemplate;

	@BeforeAll
	public static void init() {
		resttemplate = new RestTemplate();
	}

	@BeforeEach
	
	public void setUp() {
		baseUrl = String.format(formattingString, baseUrl, port);
	}

	@Test
	@Order(1)
	public void testSaveFirstTodo() {
			Todo todo = new Todo("First task for the day", false);
			ResponseEntity<Todo> response = resttemplate.postForEntity(baseUrl, todo, Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			assertThat(response.getBody().getId()).isEqualTo(1);
			assertThat(response.getBody().getTask()).isEqualTo(todo.getTask());
	}
	
	@Test
	@Order(2)
	public void testSaveSecondTodo() {
			Todo todo = new Todo("Second task for the day", false);
			ResponseEntity<Todo> response = resttemplate.postForEntity(baseUrl, todo, Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			assertThat(response.getBody().getId()).isEqualTo(2);
			assertThat(response.getBody().getTask()).isEqualTo(todo.getTask());
	}
	
	
	@Test
	@Order(3)
	public void testSaveThirdTodo() {
			Todo todo = new Todo("Third task for the day", false);
			ResponseEntity<Todo> response = resttemplate.postForEntity(baseUrl, todo, Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			assertThat(response.getBody().getId()).isEqualTo(3);
			assertThat(response.getBody().getTask()).isEqualTo(todo.getTask());
	}
	
	@Test
	@Order(4)
	public void testGetTodoById() {
			ResponseEntity<Todo> response = resttemplate.getForEntity(baseUrl + "/1", Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getId()).isEqualTo(1);
			assertThat(response.getBody().getTask()).isEqualTo("First task for the day");
	}
	
	@Test
	@Order(5)
	public void testGetTodoList() {
			ResponseEntity<TodoList> response = resttemplate.getForEntity(baseUrl, TodoList.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getTodos().size()).isEqualTo(3);
	}
	
	@Test
	@Order(6)
	public void testUpdateFirstTodo() {
			 Map<String, Object> fields = new HashMap<String, Object>();
			 fields.put("task", "First task for the day is done");
			 fields.put("completed", true);
			resttemplate.put(baseUrl+"/{id}", fields, 1);
			ResponseEntity<Todo> response = resttemplate.getForEntity(baseUrl + "/1", Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getId()).isEqualTo(1);
			assertThat(response.getBody().getTask()).isEqualTo(fields.get("task"));
			assertThat(response.getBody().isCompleted()).isEqualTo(fields.get("completed"));
	}
	
	@Test
	@Order(7)
	public void testDeleteTodo() {
		resttemplate.delete(baseUrl+"/{id}", 2);
		ResponseEntity<TodoList> response = resttemplate.getForEntity(baseUrl, TodoList.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTodos().size()).isEqualTo(2);
	}

}
