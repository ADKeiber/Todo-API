package com.adk.todo.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="SubTask")
@Data @NoArgsConstructor @AllArgsConstructor
public class Subtask {

	@Id
	@UuidGenerator
	private String id;
	private String description;
	private TaskStatus status;
	private String parentTaskId;
	
}
