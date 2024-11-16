package com.adk.todo.util;

import java.util.LinkedList;
import java.util.List;

import com.adk.todo.dto.SubtaskDTO;
import com.adk.todo.dto.TaskDTO;
import com.adk.todo.dto.UserDTO;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.User;

public class DTOMapper {
	
	public UserDTO mapToDTO(User user) {
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
	
	public TaskDTO mapToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(task.getId());
		taskDTO.setUserId(task.getUser().getId());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setStatus(task.getStatus());
		List<SubtaskDTO> subtasks = new LinkedList<>();
		for(int i = 0; i < task.getSubtasks().size(); i++) {
			subtasks.add(mapToDTO(task.getSubtasks().get(i)));
		}
		taskDTO.setSubtasks(subtasks);
		return taskDTO;
	}
	
	public SubtaskDTO mapToDTO(Subtask subtask) {
		SubtaskDTO subtaskDTO = new SubtaskDTO();
		subtaskDTO.setId(subtask.getId());
		subtaskDTO.setParentTaskId(subtask.getParentTask().getId());
		subtaskDTO.setDescription(subtask.getDescription());
		subtaskDTO.setStatus(subtask.getStatus());
		return subtaskDTO;
	}
}
