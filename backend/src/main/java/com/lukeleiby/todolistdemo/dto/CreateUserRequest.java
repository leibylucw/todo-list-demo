package com.lukeleiby.todolistdemo.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
	@NotBlank
	private String name;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
