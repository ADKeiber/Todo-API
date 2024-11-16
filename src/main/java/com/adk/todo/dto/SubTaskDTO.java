package com.adk.todo.dto;

import java.util.List;

import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class SubtaskDTO {
	String id;
	String parentTaskId;
	String description;
	TaskStatus status;
}