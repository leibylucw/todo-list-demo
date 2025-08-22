// src/main/java/com/lukeleiby/todolistdemo/dto/TaskResponse.java
package com.lukeleiby.todolistdemo.dto;

public class TaskResponse {
	private Long id;
	private String name;
	private String description;
	private Long userId;

	public TaskResponse() {}

	public TaskResponse(Long id, String name, String description, Long userId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public Long getUserId() { return userId; }
	public void setUserId(Long userId) { this.userId = userId; }
}
