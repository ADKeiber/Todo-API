package com.adk.todo.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.adk.blog.model.Tag;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="User")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {

	@Id
	@UuidGenerator
	private String id;
	private String username;
	private String password;
	
	@ManyToMany()
	@JoinTable(
			name = "user_task",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "task_id"))
	private List<Task> tasks;
	
}
