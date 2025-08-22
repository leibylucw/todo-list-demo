// src/main/java/com/lukeleiby/todolistdemo/dto/UpdateTaskRequest.java
package com.lukeleiby.todolistdemo.dto;

public class UpdateTaskRequest {
	private String name;        // optional
	private String description; // optional
	private Long userId;        // optional; reassigns owner

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public Long getUserId() { return userId; }
	public void setUserId(Long userId) { this.userId = userId; }
}
