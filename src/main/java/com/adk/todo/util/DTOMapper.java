package com.adk.todo.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.adk.todo.dto.SubtaskDTO;
import com.adk.todo.dto.TaskDTO;
import com.adk.todo.dto.UserDTO;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.User;

/**
 * Mapping class that helps Database entity objects map into the Data transfer object equivalent
 */
@Component
public class DTOMapper {
	
	/**
	 * Maps a {@link User} to a {@link UserDTO}
	 * @param user {@link User} the user to map
	 * @return {@link UserDTO} the mapped result of the user
	 */
	public static UserDTO mapToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		List<TaskDTO> tasks = new LinkedList<>();
		for(int i = 0; i < user.getTasks().size(); i++) {
			tasks.add(mapToDTO(user.getTasks().get(i)));
		}
		userDTO.setTasks(tasks);
		return userDTO;
	}
	
	/**
	 * Maps a {@link Task} to a {@link TaskDTO}
	 * @param task {@link Task} the task to map
	 * @return {@link TaskDTO} the mapped result of the task
	 */
	public static TaskDTO mapToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(task.getId());
		taskDTO.setUserId(task.getUserId());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setStatus(task.getStatus());
		List<SubtaskDTO> subtasks = new LinkedList<>();
		for(int i = 0; i < task.getSubtasks().size(); i++) {
			subtasks.add(mapToDTO(task.getSubtasks().get(i)));
		}
		taskDTO.setSubtasks(subtasks);
		return taskDTO;
	}
	
	/**
	 * Maps a {@link Subtask} to a {@link SubTaskDTO}
	 * @param subtask {@link Subtask} the subtask to map
	 * @return {@link SubTaskDTO} the mapped result of the subtask
	 */
	public static SubtaskDTO mapToDTO(Subtask subtask) {
		SubtaskDTO subtaskDTO = new SubtaskDTO();
		subtaskDTO.setId(subtask.getId());
		subtaskDTO.setParentTaskId(subtask.getParentTaskId());
		subtaskDTO.setDescription(subtask.getDescription());
		subtaskDTO.setStatus(subtask.getStatus());
		return subtaskDTO;
	}
}
