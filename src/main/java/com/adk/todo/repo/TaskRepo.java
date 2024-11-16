package com.adk.todo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;

@Repository
public interface TaskRepo extends ListCrudRepository<Task, String>{
	
	@Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.status = :status")
	public List<Task> getTasksByStatusForUser(String userId, TaskStatus status);
}
