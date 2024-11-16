package com.adk.todo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;

@Repository
public interface SubtaskRepo extends ListCrudRepository<Subtask, String>{
}
