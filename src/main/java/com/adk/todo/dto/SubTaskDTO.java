package com.adk.todo.dto;

import com.adk.todo.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for subtask
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class SubtaskDTO {
	String id;
	String parentTaskId;
	String description;
	TaskStatus status;
}
