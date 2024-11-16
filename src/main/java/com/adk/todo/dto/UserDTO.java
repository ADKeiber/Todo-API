package com.adk.todo.dto;

import java.util.List;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {	
	String id;
	String username;
	List<TaskDTO> tasks;
}
