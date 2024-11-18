package com.adk.todo.service;

import java.util.List;

import com.adk.todo.dto.SubtaskDTO;
import com.adk.todo.dto.TaskDTO;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;

/**
 * Interface holding critical methods to interact with tasks
 */
public interface ITaskService {
	
	/**
	 * Updates a task based on its ID
	 * @param task {@link Task} task to update
	 * @return {@link TaskDTO} of the mapped task
	 */
	public TaskDTO updateTask(Task task);
	
	/**
	 * Gets tasks for a user based on passed in userID and statuses
	 * <p>
	 * NOTE: statuses can be an empty list to get all tasks for a user
	 * @param userId {@link String} id of the user who's tasks we are retrieving 
	 * @param status List of {@link TaskStatus} statuses to filter the tasks on
	 * @return List of {@link TaskDTO} of the tasks associated with the user with the given id and have the one of the statuses
	 */
	public List<TaskDTO> getTaskByStatusesAndUser(String userId, List<TaskStatus> status);
	
	/**
	 * Gets an {@link Task} by its ID
	 * @param taskId {@link String} id of the retrieved task
	 * @return {@link Task} the task with the given ID
	 */
	public Task getTaskById(String taskId);
	
	/**
	 * Adds a subtask to a task
	 * @param task {@link Task} task that will have a subtask added
	 * @param subtask {@link Subtask} that will be added to the task
	 * @return {@link TaskDTO} the subtask added to the task
	 */
	public TaskDTO addSubtaskToTask(Task task, Subtask subtask);
	
	/**
	 * Updates a subtask based on its ID
	 * @param subtask {@link Subtask} the subtask to update
	 * @return {@link SubtaskDTO} the saved subtask converted to its {@link SubTaskDTO}
	 */
	public SubtaskDTO updateSubtask(Subtask subtask);
	
	/**
	 * Deletes a task by its ID
	 * @param taskId {@link String} the id of the task
	 */
	public void deleteTask(String taskId);
	
	/**
	 * Deletes a subtask
	 * @param subtaskId {@link String} the id of the subtask
	 */
	public void deleteSubtask(String subtaskId);
}
