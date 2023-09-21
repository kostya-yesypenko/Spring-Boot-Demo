package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;
import com.example.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	}