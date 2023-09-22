package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.TaskLog;

@Repository
public interface TaskLogRepository extends JpaRepository<TaskLog, Integer>{
	}