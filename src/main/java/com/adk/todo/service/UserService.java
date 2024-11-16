package com.adk.todo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adk.blog.errorhandling.EntityNotFoundException;
import com.adk.todo.errorhandling.IncorrectPasswordException;
import com.adk.todo.model.Task;
import com.adk.todo.model.User;
import com.adk.todo.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class UserService implements IUserService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User returnedUser = userRepo.save(user);
		returnedUser.setPassword(null);
		return returnedUser;
	}

	@Override
	public User login(User user) throws Exception {
		User returnedUser = userRepo.findByUsername(user.getUsername());
		if(returnedUser == null){
			throw new EntityNotFoundException(User.class, "username", user.getUsername());
		} else if(!passwordEncoder.matches(user.getPassword(), returnedUser.getPassword())) {
			throw new IncorrectPasswordException(user.getUsername());
		}
		returnedUser.setPassword(null);
		return returnedUser;
	}

	@Override
	public User getUserById(String id) throws Exception {
		return userRepo.findById(id).get();
	}

	@Override
	public User addTaskToUser(User user, Task task) {
		List<Task> tasks = user.getTasks();
		tasks.add(task);
		user.setTasks(tasks);
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		return savedUser;
	}
}
