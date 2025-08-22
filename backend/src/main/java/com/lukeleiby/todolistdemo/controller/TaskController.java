// src/main/java/com/lukeleiby/todolistdemo/controller/TaskController.java
package com.lukeleiby.todolistdemo.controller;

import com.lukeleiby.todolistdemo.dto.TaskResponse;
import com.lukeleiby.todolistdemo.dto.CreateTaskRequest;
import com.lukeleiby.todolistdemo.dto.UpdateTaskRequest;
import com.lukeleiby.todolistdemo.model.Task;
import com.lukeleiby.todolistdemo.model.User;
import com.lukeleiby.todolistdemo.repository.TaskRepository;
import com.lukeleiby.todolistdemo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/tasks")
	public List<TaskResponse> getAllTasks() {
		return taskRepository.findAll()
			.stream()
			.map(this::toTaskResponse)
			.toList();
	}

	@PostMapping("/users/{userId}/tasks")
	public ResponseEntity<TaskResponse> createTaskForUser(@PathVariable Long userId, @Valid @RequestBody CreateTaskRequest body) {
		User owner = userRepository.findById(userId).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		Task task = new Task();
		task.setName(body.getName());
		task.setDescription(body.getDescription());
		task.setUser(owner);

		Task saved = taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(toTaskResponse(saved));
	}

	@PatchMapping("/tasks/{taskId}")
	public TaskResponse updateTask(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskRequest body) {
		Task task = taskRepository.findById(taskId).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

		if (body.getName() != null && !body.getName().isBlank()) {
			task.setName(body.getName());
		}
		if (body.getDescription() != null) {
			task.setDescription(body.getDescription());
		}
		if (body.getUserId() != null) {
			User newOwner = userRepository.findById(body.getUserId()).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "New owner user not found"));
			task.setUser(newOwner);
		}
		return toTaskResponse(taskRepository.save(task));
	}

	@DeleteMapping("/tasks/{taskId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTask(@PathVariable Long taskId) {
		if (!taskRepository.existsById(taskId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
		}
		taskRepository.deleteById(taskId);
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
