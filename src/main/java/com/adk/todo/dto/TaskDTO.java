package com.adk.todo.dto;

import java.util.List;

import com.adk.todo.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Task
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class TaskDTO {
	String id;
	String userId;
	String description;
	TaskStatus status;
	List<SubtaskDTO> subtasks;
}
