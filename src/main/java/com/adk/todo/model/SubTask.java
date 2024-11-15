package com.adk.todo.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Id;

public class SubTask {

	@Id
	@UuidGenerator
	private String id;
	private String description;
	private TaskStatus status;
}
