package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.TaskLog;
import com.example.repository.TaskLogRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TaskLogService extends BaseService<TaskLog, Integer> {
	
	@Autowired
	private TaskLogRepository tlogRepo;	

	@PostConstruct
	private void init() {
		setRepo(tlogRepo);
	}		
}