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
 * Database object for user
 */
@Entity
@Table(name="User")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {

	@Id
	@UuidGenerator
	private String id;
	private String username;
	private String password;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Task> tasks;
	
}
