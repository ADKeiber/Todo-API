package com.adk.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adk.blog.errorhandling.ApiError;
import com.adk.todo.dto.UserDTO;
import com.adk.todo.model.Subtask;
import com.adk.todo.model.Task;
import com.adk.todo.model.TaskStatus;
import com.adk.todo.model.User;
import com.adk.todo.service.TaskService;
import com.adk.todo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@RestController
@Tag(name = "Todo API Endpoints")
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Operation(summary = "Create a new user login", description = "Creates a new user by taking in a JSON User Object. If required fields are blank/null inside of the request body or username already exists with an API Error will be returned. Fields Required: username, password", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\n" + "	\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e6\",\n"
							+ "	\"username\":\"Example Username\",\n" + "	\"tasks\":\"[]\"\n" + "}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"BAD_REQUEST\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n"
							+ "		\"debugMessage\":\"User was missing value of field 'username' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })),
			@ApiResponse(description = "Conflict/ Username already Exists", responseCode = "409", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"CONFLICT\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"The username 'Example Username' exists already!\",\n"
							+ "		\"debugMessage\": null,\n" + "	}\n" + "}") })) })
	@PostMapping("/createUser")
	public ResponseEntity<Object> create(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
	}

	@Operation(summary = "Log in and authenticate user", description = "Allows a user to log in by taking in a JSON User Object. If required fields are blank/null inside of the request body or password is incorrect already exists with an API Error will be returned. Fields Required: username, password", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"id\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "    \"username\": \"admin\",\r\n" + "    \"tasks\": [\r\n" + "        {\r\n"
							+ "            \"id\": \"8ad48580-2748-49e0-b7e9-82366c124779\",\r\n"
							+ "            \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "            \"description\": \"Task 1\",\r\n" + "            \"status\": \"TODO\",\r\n"
							+ "            \"subtasks\": []\r\n" + "        },\r\n" + "        {\r\n"
							+ "            \"id\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "            \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "            \"description\": \"Task 2\",\r\n" + "            \"status\": \"TODO\",\r\n"
							+ "            \"subtasks\": [\r\n" + "                {\r\n"
							+ "                    \"id\": \"02735e20-19ac-4f46-b22d-df463c4a7595\",\r\n"
							+ "                    \"parentTaskId\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "                    \"description\": \"Subtask 1\",\r\n"
							+ "                    \"status\": \"IN_PROGRESS\"\r\n" + "                }\r\n"
							+ "            ]\r\n" + "        }\r\n" + "    ]\r\n" + "}") })),
			@ApiResponse(description = "Not Found/ No User with that username", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"NOT_FOUND\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\": \"Tag was not found for parameters {username=Example Username}\",\n"
							+ "		\"debugMessage\":null,\n" + "	}\n" + "}") })),
			@ApiResponse(description = "Unauthorized/ Incorrect Password", responseCode = "401", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"UNAUTHORIZED\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"The username 'Example Username' exists but the password is incorrect!\",\n"
							+ "		\"debugMessage\": null,\n" + "	}\n" + "}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"BAD_REQUEST\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n"
							+ "		\"debugMessage\":\"User was missing value of field 'username' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })), })
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
	}

	@Operation(summary = "Get tasks for a user", description = "Retrieves a list of tasks based on userID and optional status filters. "
			+ "If user can't be found with given userId an API Error will be returned.", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Task.class)), examples = {
							@ExampleObject(value = "[\r\n" + "    {\r\n"
									+ "        \"id\": \"8ad48580-2748-49e0-b7e9-82366c124779\",\r\n"
									+ "        \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
									+ "        \"description\": \"Task 1\",\r\n" + "        \"status\": \"TODO\",\r\n"
									+ "        \"subtasks\": []\r\n" + "    },\r\n" + "    {\r\n"
									+ "        \"id\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
									+ "        \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
									+ "        \"description\": \"Task 2\",\r\n" + "        \"status\": \"TODO\",\r\n"
									+ "        \"subtasks\": [\r\n" + "            {\r\n"
									+ "                \"id\": \"02735e20-19ac-4f46-b22d-df463c4a7595\",\r\n"
									+ "                \"parentTaskId\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
									+ "                \"description\": \"Subtask 1\",\r\n"
									+ "                \"status\": \"IN_PROGRESS\"\r\n" + "            }\r\n"
									+ "        ]\r\n" + "    }\r\n" + "]") })),
					@ApiResponse(description = "Not Found/ No User with that id", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
									+ "		\"status\":\"NOT_FOUND\",\n"
									+ "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
									+ "		\"message\": \"User was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n"
									+ "		\"debugMessage\":null,\n" + "	}\n" + "}") })), })
	@GetMapping("/getTasks/{userId}")
	public ResponseEntity<Object> getTasksByUserId(@PathVariable String userId,
			@RequestParam(required = false) List<TaskStatus> statuses) throws Exception {
		return new ResponseEntity<>(taskService.getTaskByStatusesAndUser(userId, statuses), HttpStatus.OK);
	}

	@Operation(summary = "Add a task to a user", description = "Adds a task to a user by taking in a JSON Task Object and a userId. If required fields are blank/null inside of the request body or userId doesn't exist an API Error will be returned. Fields Required: username, password", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"id\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "    \"username\": \"admin\",\r\n" + "    \"tasks\": [\r\n" + "        {\r\n"
							+ "            \"id\": \"dbad7957-ddbd-47d1-99a5-fd36f0d1cadd\",\r\n"
							+ "            \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "            \"description\": \"Task 3\",\r\n" + "            \"status\": \"TODO\",\r\n"
							+ "            \"subtasks\": []\r\n" + "        }\r\n" + "    ]\r\n" + "}") })),
			@ApiResponse(description = "Not Found/ No User with that id", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"NOT_FOUND\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\": \"User was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n"
							+ "		\"debugMessage\":null,\n" + "	}\n" + "}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"BAD_REQUEST\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n"
							+ "		\"debugMessage\":\"Task was missing value of field 'username' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })), })
	@PostMapping("/addTask/{userId}")
	public ResponseEntity<Object> addTask(@PathVariable String userId, @RequestBody Task task) throws Exception {
		return new ResponseEntity<>(userService.addTaskToUser(userService.getUserById(userId), task), HttpStatus.OK);
	}

	@Operation(summary = "Update existing task", description = "Updates an existing task by taking in a JSON Task Object. If required fields are blank/null inside of the request body or userId doesn't exist an API Error will be returned. Fields Required: description, status, userId", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"id\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "    \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "    \"description\": \"Task 2\",\r\n" + "    \"status\": \"IN_PROGRESS\",\r\n"
							+ "    \"subtasks\": []\r\n" + "}") })),
			@ApiResponse(description = "Not Found/ No User with that id", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"NOT_FOUND\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\": \"User was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n"
							+ "		\"debugMessage\":null,\n" + "	}\n" + "}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"BAD_REQUEST\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n"
							+ "		\"debugMessage\":\"Task was missing value of field 'description' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })), })
	@PostMapping("/updateTask")
	public ResponseEntity<Object> updateTaskStatus(@RequestBody Task task) throws Exception {
		return new ResponseEntity<>(taskService.updateTask(task), HttpStatus.OK);
	}

	@Operation(summary = "Add a subtask to a task", description = "Adds a subtask to a task by taking in a JSON Subtask Object and a userId. If required fields are blank/null inside of the request body or taskId doesn't exist an API Error will be returned. Fields Required: description, status", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"id\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "    \"userId\": \"1c7740e6-89c8-4a00-9373-7a944acf6844\",\r\n"
							+ "    \"description\": \"Task 2\",\r\n" + "    \"status\": \"IN_PROGRESS\",\r\n"
							+ "    \"subtasks\": [\r\n" + "        {\r\n"
							+ "            \"id\": \"b799b4a4-edc5-4087-84ca-c62a49c10d20\",\r\n"
							+ "            \"parentTaskId\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "            \"description\": \"Subtask 2\",\r\n" + "            \"status\": \"TODO\"\r\n"
							+ "        }\r\n" + "    ]\r\n" + "}") })),
			@ApiResponse(description = "Not Found/ No Task with that id", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"NOT_FOUND\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\": \"Task was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n"
							+ "		\"debugMessage\":null,\n" + "	}\n" + "}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"BAD_REQUEST\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n"
							+ "		\"debugMessage\":\"Subtask was missing value of field 'description' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })), })
	@PostMapping("/addSubtask/{taskId}")
	public ResponseEntity<Object> addSubtask(@PathVariable String taskId, @RequestBody Subtask subtask)
			throws Exception {
		return new ResponseEntity<>(taskService.addSubtaskToTask(taskService.getTaskById(taskId), subtask),
				HttpStatus.OK);
	}

	@Operation(summary = "Update existing subtask", description = "Updates an existing subtask by taking in a JSON Subtask Object. If required fields are blank/null inside of the request body or parentTaskId doesn't exist an API Error will be returned. Fields Required: description, status, parentTaskId", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"id\": \"02735e20-19ac-4f46-b22d-df463c4a7595\",\r\n"
							+ "    \"parentTaskId\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "    \"description\": \"Subtask 1\",\r\n" + "    \"status\": \"IN_PROGRESS\"\r\n"
							+ "}") })),
			@ApiResponse(description = "Not Found/ No User with that id", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"NOT_FOUND\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\": \"Task was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n"
							+ "		\"debugMessage\":null,\n" + "	}\n" + "}") })),
			@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
					@ExampleObject(value = "{\n" + "	\"apierror\":\n" + "	{\n"
							+ "		\"status\":\"BAD_REQUEST\",\n" + "		\"timestamp\":\"11-11-2024 10:19:28\",\n"
							+ "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n"
							+ "		\"debugMessage\":\"Subtask was missing value of field 'description' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })), })
	@PostMapping("/updateSubtask")
	public ResponseEntity<Object> updateSubtaskStatus(@RequestBody Subtask subtask) throws Exception {
		return new ResponseEntity<>(taskService.updateSubtask(subtask), HttpStatus.OK);
	}
}
