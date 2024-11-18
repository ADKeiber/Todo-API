package com.adk.todo.service;

import com.adk.todo.dto.UserDTO;
import com.adk.todo.model.Task;
import com.adk.todo.model.User;

/**
 * Interface holding critical methods to interact with users
 */
public interface IUserService {

	/**
	 * Creates a new user
	 * @param user {@link User} new user to save to DB
	 * @return {@link UserDTO} mapped user object
	 */
	public UserDTO createUser(User user);
	
	/**
	 * Authenticates a user and verifies it matches database
	 * @param user {@link} user to authenticate
	 * @return {@link UserDTO} mapped user object
	 */
	public UserDTO login(User user);
	
	/**
	 * Gets a user By passed in ID
	 * @param id {@link String} Id of the user to retrieve 
	 * @return {@link User} user that has the given ID
	 */
	public User getUserById(String id);
	
	/**
	 * Adds a task to a given user
	 * @param user {@link User} user that will have task added
	 * @param task {@link Task} task that will be added to a user
	 * @return {@link UserDTO} mapped user Object
	 */
	public UserDTO addTaskToUser(User user, Task task);
	
	/**
	 * Deletes a userc
	 * @param user {@link User} the user to delete
	 */
	public void deleteUser(User user);
}
