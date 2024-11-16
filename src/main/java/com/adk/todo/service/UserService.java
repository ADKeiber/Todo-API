package com.adk.todo.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adk.blog.errorhandling.EntityNotFoundException;
import com.adk.todo.dto.UserDTO;
import com.adk.todo.errorhandling.IncorrectPasswordException;
import com.adk.todo.model.Task;
import com.adk.todo.model.User;
import com.adk.todo.repo.UserRepo;
import com.adk.todo.util.DTOMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class UserService implements IUserService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDTO saveUser(User user) throws Exception {
		//Check to see if user exists by ID first then throw exception
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setTasks(new LinkedList<Task>());
		User returnedUser = userRepo.save(user);
		return DTOMapper.mapToDTO(returnedUser);
	}

	@Override
	public UserDTO login(User user) throws Exception {
		User returnedUser = userRepo.findByUsername(user.getUsername());
		if(returnedUser == null){
			throw new EntityNotFoundException(User.class, "username", user.getUsername());
		} else if(!passwordEncoder.matches(user.getPassword(), returnedUser.getPassword())) {
			throw new IncorrectPasswordException(user.getUsername());
		}
		System.out.println("User: " + returnedUser.toString());
		return DTOMapper.mapToDTO(returnedUser);
	}

	@Override
	public User getUserById(String id) throws Exception {
		return userRepo.findById(id).get();
	}

	@Override
	public UserDTO addTaskToUser(User user, Task task) {
		List<Task> tasks = user.getTasks();
		tasks.add(task);
		user.setTasks(tasks);
		task.setUserId(user.getId());
		System.out.println("User: " + user.toString());
		User savedUser = userRepo.save(user);
		return DTOMapper.mapToDTO(savedUser);
	}
}
