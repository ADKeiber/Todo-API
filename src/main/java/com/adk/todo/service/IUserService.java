package com.adk.todo.service;

import com.adk.todo.model.User;

public interface IUserService {

	public User saveUser(User user) throws Exception;
	
	public User login(User user) throws Exception;
}
