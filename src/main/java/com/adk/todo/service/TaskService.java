package com.adk.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
import com.adk.todo.repo.TaskRepo;

@Service
public class TaskService implements ITaskService{
	
	@Autowired
	public TaskRepo taskRepo;

	@Override
	public Task updateTask(Task task) {
		Task returnedTask = taskRepo.save(task);
		return returnedTask;
	}

	@Override
	public List<Task> getTaskByStatusAndUser(String userId, TaskStatus status) {
		return taskRepo.getTasksByStatusForUser(userId, status);
	}

}
