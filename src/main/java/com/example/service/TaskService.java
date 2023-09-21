package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Task;
import com.example.repository.TaskRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TaskService extends BaseService<Task, Integer> {
	
	@Autowired
	private TaskRepository taskRepo;	

	@PostConstruct
	private void init() {
		setRepo(taskRepo);
	}		
}