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

	/**
	 * Takes in a new user and saves it to the database
	 * @param user	{@link User} The new user to be saved
	 * @return {@link ApiError} if there is an error creating the user, otherwise {@link UserDTO}
	 * @throws Exception if username or password is missing, or user with passed in username already exists in the database
	 */
	@Operation(summary = "Create a new user login", description = "Creates a new user by taking in a JSON User Object. If required fields are blank/null inside of the request body or username already exists an API Error will be returned. Fields Required: username, password", responses = {
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

	/**
	 * Authenticates the user has the correct username and password combo
	 * @param user {@link User} the user to authenticate
	 * @return {@link ApiError} if there is an error authenticating the user, otherwise {@link UserDTO}
	 * @throws Exception if username or password is missing, user doesn't exist in the database, or the password doesn't match the password in the DB
	 */
	@Operation(summary = "Log in and authenticate user", description = "Allows a user to log in by taking in a JSON User Object. If required fields are blank/null inside of the request body, the password is incorrect, or the username doesn't exist an API Error will be returned. Fields Required: username, password", responses = {
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
							+ "		\"message\": \"User was not found for parameters {username=Example Username}\",\n"
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

	/**
	 * Gets the tasks of a user by the UserID. The user can pass in statuses to filter tasks based on their status
	 * @param userId {@link String} the userId to get the tasks of
	 * @param statuses {@link TaskStatus} optional task status that allows the user to filter based on specific statuses 
	 * @return {@link ApiError} if there is an error retrieving the user, otherwise list {@link TaskDTO}
	 * @throws Exception if user doesn't exist with the given userId
	 */
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
	public ResponseEntity<Object> getTasksByUserId(@PathVariable String userId, @RequestParam(required = false) List<TaskStatus> statuses) throws Exception {
		return new ResponseEntity<>(taskService.getTaskByStatusesAndUser(userId, statuses), HttpStatus.OK);
	}

	/**
	 * Adds a task to a user based on UserId
	 * @param userId {@link String} the ID of the user to add the task to
	 * @param task {@link Task} the task to add to the user
	 * @return {@link ApiError} if there is an error retrieving the user or required fields are missing from the task, otherwise {@link UserDTO}
	 * @throws Exception if user doesn't exists with the passed in id, or the task is missing its description/status
	 */
	@Operation(summary = "Add a task to a user", description = "Adds a task to a user by taking in a JSON Task Object and a userId. If required fields are blank/null inside of the request body or userId doesn't exist an API Error will be returned. Fields Required: description, status", responses = {
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
							+ "		\"debugMessage\":\"Task was missing value of field 'description' which is of class java.lang.String\",\n"
							+ "	}\n" + "}") })), })
	@PostMapping("/addTask/{userId}")
	public ResponseEntity<Object> addTask(@PathVariable String userId, @RequestBody Task task) throws Exception {
		return new ResponseEntity<>(userService.addTaskToUser(userService.getUserById(userId), task), HttpStatus.OK);
	}

	/**
	 * Updates a task
	 * <p>
	 * NOTE: Task is updated based on the ID that is part of the passed in Task
	 * @param task {@link Task} the task getting updated
	 * @return {@link ApiError} if there is an error finding the task with the Id in passed in Task, otherwise {@link TaskDTO}
	 * @throws Exception if task with the passed in id doesn't exist, or the task is missing its description/status
	 */
	@Operation(summary = "Update existing task", description = "Updates an existing task by taking in a JSON Task Object. If required fields are blank/null inside of the request body or post.userId doesn't exist an API Error will be returned. Fields Required: description, status, userId", responses = {
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

	/**
	 * Adds a subtask to a task with the passed in ID
	 * @param taskId {@link String} the id of the task the subtask will be added to
	 * @param subtask {@link Subtask} the subtask to add to the task
	 * @return {@link ApiError} if there is an error finding the task with the Id or subtask is missing required fields, otherwise {@link TaskDTO}
	 * @throws Exception if task with given id doesn't exists, or subtask is missing required fields (description and status)
	 */
	@Operation(summary = "Add a subtask to a task", description = "Adds a subtask to a task by taking in a JSON Subtask Object and a taskId. If required fields are blank/null inside of the request body or taskId doesn't exist an API Error will be returned. Fields Required: description, status", responses = {
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

	/**
	 * Updates a subtask
	 * <p>
	 * NOTE: Subtask is updated based on the ID that is part of the passed in Subtask
	 * @param subtask {@link Subtask} the subtask to update
	 * @return {@link ApiError} if there is an error finding the subtask with the Id or subtask is missing required fields, otherwise {@link TaskDTO}
	 * @throws Exception if subtask with the passed in id doesn't exist or the subtask is missing its description/status
	 */
	@Operation(summary = "Update existing subtask", description = "Updates an existing subtask by taking in a JSON Subtask Object. If required fields are blank/null inside of the request body or parentTaskId doesn't exist an API Error will be returned. Fields Required: description, status, subtask.parentTaskId", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "    \"id\": \"02735e20-19ac-4f46-b22d-df463c4a7595\",\r\n"
							+ "    \"parentTaskId\": \"ebab59b4-a148-4ff0-b1fb-95c71d8ca0c3\",\r\n"
							+ "    \"description\": \"Subtask 1\",\r\n" + "    \"status\": \"IN_PROGRESS\"\r\n"
							+ "}") })),
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
	@PostMapping("/updateSubtask")
	public ResponseEntity<Object> updateSubtaskStatus(@RequestBody Subtask subtask) throws Exception {
		return new ResponseEntity<>(taskService.updateSubtask(subtask), HttpStatus.OK);
	}
}
