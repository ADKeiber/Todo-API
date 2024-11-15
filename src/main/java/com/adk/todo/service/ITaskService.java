package com.adk.todo.service;

import java.util.List;

import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;

public interface ITaskService {
	public Task updateTask(Task task);
	public List<Task> getTaskByStatusAndUser(String userId, TaskStatus status);
}
