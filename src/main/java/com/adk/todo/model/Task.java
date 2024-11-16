package com.adk.todo.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Task")
@Data @NoArgsConstructor @AllArgsConstructor
public class Task {

	@Id
	@UuidGenerator
	private String id;
	private String description;
	private TaskStatus status;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_id", nullable=false)
//	@JsonIgnoreProperties({"username", "password", "tasks"})
	private User user;
	
	@OneToMany(mappedBy="parentTask")
	private List<Subtask> subtasks;
}
