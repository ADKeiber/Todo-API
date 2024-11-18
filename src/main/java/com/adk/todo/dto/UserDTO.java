package com.adk.todo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {	
	String id;
	String username;
	List<TaskDTO> tasks;
}
