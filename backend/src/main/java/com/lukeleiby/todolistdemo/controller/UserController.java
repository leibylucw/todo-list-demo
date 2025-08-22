// src/main/java/com/lukeleiby/todolistdemo/controller/UserController.java
package com.lukeleiby.todolistdemo.controller;

import com.lukeleiby.todolistdemo.dto.UserResponse;
import com.lukeleiby.todolistdemo.dto.TaskResponse;
import com.lukeleiby.todolistdemo.dto.CreateUserRequest;
import com.lukeleiby.todolistdemo.dto.UpdateUserNameRequest;
import com.lukeleiby.todolistdemo.model.User;
import com.lukeleiby.todolistdemo.model.Task;
import com.lukeleiby.todolistdemo.repository.UserRepository;
import com.lukeleiby.todolistdemo.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
	private final UserRepository userRepository;
	private final TaskRepository taskRepository;

	public UserController(UserRepository userRepository, TaskRepository taskRepository) {
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
	}

	@GetMapping("/users")
	public List<UserResponse> getUsers() {
		return userRepository.findAll()
			.stream()
			.map(this::toUserResponse)
			.toList();
	}

	@GetMapping("/users/{id}")
	public UserResponse getUserById(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		return toUserResponse(user);
	}

	@GetMapping("/users/{id}/tasks")
	public List<TaskResponse> getTasksForUser(@PathVariable Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		return taskRepository.findByUserId(id)
			.stream()
			.map(this::toTaskResponse)
			.toList();
	}

	@PostMapping("/users")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest body) {
		User user = new User();
		user.setName(body.getName());
		User saved = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(toUserResponse(saved));
	}

	@PatchMapping("/users/{id}")
	public UserResponse updateUserName(@PathVariable Long id, @Valid @RequestBody UpdateUserNameRequest body) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		user.setName(body.getName());
		return toUserResponse(userRepository.save(user));
	}

	@DeleteMapping("/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		userRepository.deleteById(id);
	}

	private UserResponse toUserResponse(User u) {
		UserResponse r = new UserResponse();
		r.setId(u.getId());
		r.setName(u.getName());
		return r;
	}

	private TaskResponse toTaskResponse(Task t) {
		TaskResponse r = new TaskResponse();
		r.setId(t.getId());
		r.setName(t.getName());
		r.setDescription(t.getDescription());
		r.setUserId(t.getUser().getId());
		return r;
	}
}
