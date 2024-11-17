package com.adk.todo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.todo.dto.SubtaskDTO;
import com.adk.todo.dto.TaskDTO;
import com.adk.todo.errorhandling.EntityNotFoundException;
import com.adk.todo.errorhandling.FieldBlankException;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
import com.adk.todo.model.User;
import com.adk.todo.repo.SubtaskRepo;
import com.adk.todo.repo.TaskRepo;
import com.adk.todo.repo.UserRepo;
import com.adk.todo.util.DTOMapper;

@Service
public class TaskService implements ITaskService{
	
	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
	private SubtaskRepo subtaskRepo;
	
	@Autowired
	private UserRepo userRepo; 

	@Override
	public TaskDTO updateTask(Task task) {
		Optional<Task> idTask = taskRepo.findById(task.getId());
		
		//Verifies task id exists in repo
		if(idTask.isEmpty())
			throw new EntityNotFoundException(Task.class, "ID", String.class.toString());
		
		//Verifies Required fields are present in task object
		if( task.getDescription() == null || task.getDescription().isBlank())
			throw new FieldBlankException(Task.class, "Description", String.class.toString());
		if( task.getStatus() == null)
			throw new FieldBlankException(Task.class, "Status", TaskStatus.class.toString());
		
		task.setUserId(idTask.get().getUserId());
		
		Task returnedTask = taskRepo.save(task);
		
		return DTOMapper.mapToDTO(returnedTask);
	}
	
	@Override
	public List<TaskDTO> getTaskByStatusesAndUser(String userId, List<TaskStatus> status) {
		
		Optional<User> user = userRepo.findById(userId);
		//Verifies user Exists
		if(user.isEmpty())
			throw new EntityNotFoundException(User.class, "ID", userId);

		List<Task> tasks;
		if(status == null)
			tasks = taskRepo.getTasksByUser(userId);
		else {
			tasks = taskRepo.getTasksByStatusForUser(userId, status.get(0));
			for(int i = 1; i < status.size(); i++) {
				tasks.addAll(taskRepo.getTasksByStatusForUser(userId, status.get(i)));
			}
		}
		List<TaskDTO> tasksDTO = new LinkedList<>();
		for(int i = 0 ; i < tasks.size(); i++) {
			tasksDTO.add(DTOMapper.mapToDTO(tasks.get(i)));
		}
		return tasksDTO;
	}
	
	@Override
	public Task getTaskById(String taskId) {
		Optional<Task> task = taskRepo.findById(taskId);
		//Verifies user Exists
		if(task.isEmpty())
			throw new EntityNotFoundException(Task.class, "ID", taskId);
		return task.get();
	}

	@Override
	public TaskDTO addSubtaskToTask(Task task, Subtask subtask) {
		//Verifies Required fields are present in subtask object
		if( subtask.getDescription() == null || subtask.getDescription().isBlank())
			throw new FieldBlankException(Subtask.class, "Description", String.class.toString());
		if( subtask.getStatus() == null)
			throw new FieldBlankException(Subtask.class, "Status", TaskStatus.class.toString());
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
		//Verifies Required fields are present in subtask object
		if( subtask.getDescription() == null || subtask.getDescription().isBlank())
			throw new FieldBlankException(Subtask.class, "Description", String.class.toString());
		if( subtask.getStatus() == null)
			throw new FieldBlankException(Subtask.class, "Status", TaskStatus.class.toString());
		Subtask returnedSubtask = subtaskRepo.save(subtask);
		return DTOMapper.mapToDTO(returnedSubtask);
	}

}
