package com.adk.todo.repo;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.adk.todo.model.Subtask;

@Repository
public interface SubtaskRepo extends ListCrudRepository<Subtask, String>{
}
