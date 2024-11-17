package com.adk.todo.service;

import com.adk.todo.dto.UserDTO;
import com.adk.todo.model.Task;
import com.adk.todo.model.User;

public interface IUserService {

	public UserDTO createUser(User user) throws Exception;
	
	public UserDTO login(User user) throws Exception;
	
	public User getUserById(String id) throws Exception;
	
	public UserDTO addTaskToUser(User user, Task task);
}
