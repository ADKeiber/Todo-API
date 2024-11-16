package com.adk.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
import com.adk.todo.repo.SubtaskRepo;
import com.adk.todo.repo.TaskRepo;

@Service
public class TaskService implements ITaskService{
	
	@Autowired
	public TaskRepo taskRepo;
	
	@Autowired
	public SubtaskRepo subtaskRepo;

	@Override
	public Task updateTask(Task task) {
		//TODO Check task has valid fields
		Task returnedTask = taskRepo.save(task);
		return returnedTask;
	}
	
	@Override
	public List<Task> getTaskByStatusAndUser(String userId, TaskStatus status) {
		//TODO Verify task and use e
		return taskRepo.getTasksByStatusForUser(userId, status);
	}
	
	@Override
	public Task getTaskById(String taskId) {
		//TODO Verify task and use e
		return taskRepo.findById(taskId).get();
	}

	@Override
	public Subtask addSubtaskToTask(Task task, Subtask subtask) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Subtask updateTask(Subtask subtask) {
		//TODO Check task has valid fields
		Subtask returnedSubtask = subtaskRepo.save(subtask);
		return returnedSubtask;
	}

}
