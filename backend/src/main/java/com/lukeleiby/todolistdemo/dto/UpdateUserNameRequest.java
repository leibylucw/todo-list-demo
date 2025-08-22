// src/main/java/com/lukeleiby/todolistdemo/dto/UpdateUserNameRequest.java
package com.lukeleiby.todolistdemo.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserNameRequest {
	@NotBlank
	private String name;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
