package com.backend.todo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.backend.todo.model.Todo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoApplicationTests {

	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate resttemplate;

	@BeforeAll
	public static void init() {

		resttemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "/todo");
	}

	@Test
	public void testSaveTodo() {
			Todo todo = new Todo("test", false);
			System.out.println("baseURL :"+ baseUrl);
			ResponseEntity<?> response = resttemplate.postForObject(baseUrl, todo, ResponseEntity.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

}
