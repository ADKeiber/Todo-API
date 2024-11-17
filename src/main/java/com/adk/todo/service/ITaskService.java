package com.adk.todo.service;

import java.util.List;

import com.adk.todo.dto.SubtaskDTO;
import com.adk.todo.dto.TaskDTO;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;

public interface ITaskService {
	public TaskDTO updateTask(Task task);
	public List<TaskDTO> getTaskByStatusesAndUser(String userId, List<TaskStatus> status);
	public Task getTaskById(String taskId);
	public TaskDTO addSubtaskToTask(Task task, Subtask subtask);
	public SubtaskDTO updateSubtask(Subtask subtask);
}
