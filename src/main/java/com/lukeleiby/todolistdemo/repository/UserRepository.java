package com.lukeleiby.todolistdemo.repository;

import com.lukeleiby.todolistdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
