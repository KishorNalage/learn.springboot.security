package com.kishor.learn.spring.security.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private static final List<Todo> TODOS_LIST = List.of(new Todo("In28Minutes", "Learn AWS"),
			(new Todo("In28Minutes", "Get AWS Certified")));

	@GetMapping(path = "/todos")
	public List<Todo> retriveAllTodo()
	{
		return TODOS_LIST;
	}
	
	@GetMapping(path = "users/{username}/todos")
	public Todo retriveTodoForUser(@PathVariable String username)
	{
		return TODOS_LIST.get(0);
	}
	
	@PostMapping(path = "users/{username}/todos")
	public void retriveTodoForUser(@PathVariable String username,@RequestBody Todo todo)
	{
	   logger.info("Creating {} for user {}",todo,username);
	}

}
record Todo(String username,String password) {};
