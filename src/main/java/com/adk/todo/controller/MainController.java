package com.adk.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
import com.adk.todo.model.User;
import com.adk.todo.service.TaskService;
import com.adk.todo.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@RestController
@Tag(name = "Todo API Endpoints")
public class MainController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
	}
	
	@GetMapping("/getTasks/{userId}")
	public ResponseEntity<Object> getTasksByUserId(@PathVariable String userId, @RequestParam(required = false) List<TaskStatus> statuses) throws Exception {
		return new ResponseEntity<>(taskService.getTaskByStatusesAndUser(userId, statuses), HttpStatus.OK);
	}
	
	@PostMapping("/addTask/{userId}")
	public ResponseEntity<Object> addTask(@PathVariable String userId, @RequestBody Task task) throws Exception {
		return new ResponseEntity<>(userService.addTaskToUser(userService.getUserById(userId), task), HttpStatus.OK);
	}
	
	@PostMapping("/updateTask")
	public ResponseEntity<Object> updateTaskStatus(@RequestBody Task task) throws Exception {
		return new ResponseEntity<>(taskService.updateTask(task), HttpStatus.OK);
	}
	
	@PostMapping("/addSubtask/{taskId}")
	public ResponseEntity<Object> addSubtask(@PathVariable String taskId, @RequestBody Subtask subtask) throws Exception {
		return new ResponseEntity<>(taskService.addSubtaskToTask(taskService.getTaskById(taskId), subtask), HttpStatus.OK);
	}
	
	@PostMapping("/updateSubtask")
	public ResponseEntity<Object> updateSubtaskStatus(@RequestBody Subtask subtask) throws Exception {
		return new ResponseEntity<>(taskService.updateSubtask(subtask), HttpStatus.OK);
	}
}
