package com.adk.todo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adk.blog.errorhandling.EntityNotFoundException;
import com.adk.todo.dto.UserDTO;
import com.adk.todo.errorhandling.FieldBlankException;
import com.adk.todo.errorhandling.IncorrectPasswordException;
import com.adk.todo.errorhandling.UsernameAlreadyExistsException;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
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
	public UserDTO createUser(User user) throws Exception {
		
		//Checks if user exists with a specific username
		User userWithUsername = userRepo.findByUsername(user.getUsername());
		if(userWithUsername != null)
			throw new UsernameAlreadyExistsException(user.getUsername());
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setTasks(new LinkedList<Task>());
		User returnedUser = userRepo.save(user);
		return DTOMapper.mapToDTO(returnedUser);
	}

	@Override
	public UserDTO login(User user) throws Exception {
		
		User returnedUser = userRepo.findByUsername(user.getUsername());
		
		//Verifies user exists then verifies the password (after being decoded) is the same as passed in password
		if(returnedUser == null){
			throw new EntityNotFoundException(User.class, "username", user.getUsername());
		} else if(!passwordEncoder.matches(user.getPassword(), returnedUser.getPassword())) {
			throw new IncorrectPasswordException(user.getUsername());
		}
		
		return DTOMapper.mapToDTO(returnedUser);
	}

	@Override
	public User getUserById(String id) throws Exception {
		Optional<User> user = userRepo.findById(id);
		
		//Verifies user Exists
		if(user.isEmpty())
			throw new EntityNotFoundException(User.class, "User ID", id);
		
		return user.get();
	}

	@Override
	public UserDTO addTaskToUser(User user, Task task) {
		
		//Verifies Required fields are present in task object
		if( task.getDescription() == null || task.getDescription().isBlank())
			throw new FieldBlankException(Task.class, "Description", String.class.toString());
		if( task.getStatus() == null)
			throw new FieldBlankException(Task.class, "Status", TaskStatus.class.toString());
		
		List<Task> tasks = user.getTasks();
		tasks.add(task);
		user.setTasks(tasks);
		task.setUserId(user.getId());
		User savedUser = userRepo.save(user);
		return DTOMapper.mapToDTO(savedUser);
	}
}
