package com.adk.todo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adk.blog.model.Post;
import com.adk.todo.model.User;

@Repository
public interface UserRepo extends ListCrudRepository<User, String>{
	User findByUsername(String username);
}
