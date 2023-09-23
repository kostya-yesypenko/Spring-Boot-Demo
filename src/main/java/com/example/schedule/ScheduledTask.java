package com.example.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.domain.Task;
import com.example.service.TaskService;

@Component
public class ScheduledTask {
	@Autowired
	private TaskService taskService;
	
	private Task taskId1;
	
	@Scheduled(fixedDelay = 300000)
	void runTaskId1() {
		if (taskId1 == null) {
			taskId1 = taskService.get(1);
		}
		taskService.runTask(taskId1, true);
	}	
}