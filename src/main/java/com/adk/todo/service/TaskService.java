package com.adk.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.todo.dto.SubtaskDTO;
import com.adk.todo.dto.TaskDTO;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
import com.adk.todo.model.User;
import com.adk.todo.repo.SubtaskRepo;
import com.adk.todo.repo.TaskRepo;
import com.adk.todo.util.DTOMapper;

@Service
public class TaskService implements ITaskService{
	
	@Autowired
	public TaskRepo taskRepo;
	
	@Autowired
	public SubtaskRepo subtaskRepo;

	@Override
	public Task updateTask(Task task) {
		Task idTask = taskRepo.findById(task.getId()).get();
		task.setUserId(idTask.getUserId());
		//TODO Check task has valid fields
		Task returnedTask = taskRepo.save(task);
		return returnedTask;
	}
	
	@Override
	public List<Task> getTaskByStatusesAndUser(String userId, List<TaskStatus> status) {
		//TODO Verify task and use e
		List<Task> tasks;
		if(status == null)
			tasks = taskRepo.getTasksByUser(userId);
		else {
			tasks = taskRepo.getTasksByStatusForUser(userId, status.get(0));
			for(int i = 1; i < status.size(); i++) {
				tasks.addAll(taskRepo.getTasksByStatusForUser(userId, status.get(i)));
			}
		}
		return tasks;
	}
	
	@Override
	public List<Task> getTasksByUser(String userId) {
		//TODO Verify task and use e
		return taskRepo.getTasksByUser(userId);
	}
	
	@Override
	public Task getTaskById(String taskId) {
		//TODO Verify task and use e
		return taskRepo.findById(taskId).get();
	}

	@Override
	public TaskDTO addSubtaskToTask(Task task, Subtask subtask) {
		List<Subtask> subtasks = task.getSubtasks();
		subtasks.add(subtask);
		task.setSubtasks(subtasks);
		subtask.setParentTaskId(task.getId());
		Task savedTask = taskRepo.save(task);
		return DTOMapper.mapToDTO(savedTask);
	}
	
	@Override
	public SubtaskDTO updateSubtask(Subtask subtask) {
		Subtask idSubtask = subtaskRepo.findById(subtask.getId()).get();
		subtask.setParentTaskId(idSubtask.getParentTaskId());
		//TODO Check task has valid fields
		Subtask returnedSubtask = subtaskRepo.save(subtask);
		return DTOMapper.mapToDTO(returnedSubtask);
	}

}
