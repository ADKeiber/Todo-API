package com.adk.todo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
		log.info("Saving new user {} to the database", user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("Password is: {}", user.getPassword());
		User returnedUser = userRepo.save(user);
		returnedUser.setPassword(null);
		return returnedUser;
	}

	@Override
	public User login(User user) throws Exception {
//		log.info("Username is: {}", user.getUsername()); log.info("Password is: {}", user.getPassword());
		User returnedUser = userRepo.findByUsername(user.getUsername());
		if(returnedUser == null){
			log.error("User not found!");
		} else if(!passwordEncoder.matches(user.getPassword(), returnedUser.getPassword())) {
			log.error("User found but Incorrect password!");
		}
		returnedUser.setPassword(null);
		return returnedUser;
	}
	
	
}
