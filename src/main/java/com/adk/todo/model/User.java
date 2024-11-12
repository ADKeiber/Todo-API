package com.adk.todo.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
}
