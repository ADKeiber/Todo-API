package com.adk.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adk.todo.model.User;
import com.adk.todo.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@RestController
@Tag(name = "Todo API Endpoints")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
	}
}
