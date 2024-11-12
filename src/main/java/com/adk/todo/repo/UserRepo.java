package com.adk.todo.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.adk.todo.model.User;

@Repository
public interface UserRepo extends ListCrudRepository<User, String>{

}
