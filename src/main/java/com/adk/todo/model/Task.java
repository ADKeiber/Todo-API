package com.adk.todo.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Database object for task
 */
@Entity
@Table(name="Task")
@Data @NoArgsConstructor @AllArgsConstructor
public class Task {

	@Id
	@UuidGenerator
	private String id;
	private String description;
	private TaskStatus status;

    private String userId;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Subtask> subtasks;
}
