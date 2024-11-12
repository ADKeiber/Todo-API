package com.adk.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adk.todo.model.User;
import com.adk.todo.repo.UserRepo;
import com.adk.todo.util.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Transactional @RequiredArgsConstructor @Slf4j
public class UserService implements IUserService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to the database", user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
}
