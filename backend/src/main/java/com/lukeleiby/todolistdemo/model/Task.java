package com.lukeleiby.todolistdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String name;

	@Column(columnDefinition = "text")
	private String description;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Instant updatedAt;

	// getters/setters

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Instant getCreatedAt() { return createdAt; }
	public Instant setCreatedAt(Instant createdAt) { return this.createdAt = createdAt; }

	public Instant getUpdatedAt() { return updatedAt; }
	public Instant setUpdatedAt(Instant updatedAt) { return this.updatedAt = updatedAt; }
}
