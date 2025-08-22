package com.lukeleiby.todolistdemo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	// getters/setters

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public List<Task> getTasks() { return tasks; }
	public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}
