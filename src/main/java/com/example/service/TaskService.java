package com.example.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.MyNotification;
import com.example.domain.Task;
import com.example.domain.TaskLog;
import com.example.enums.Result;
import com.example.repository.TaskRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TaskService extends BaseService<Task, Integer> {
	
	@Autowired
	private TaskRepository taskRepo;	
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private TaskLogService tlogService;

	@PostConstruct
	private void init() {
		setRepo(taskRepo);
	}		
	
	public void runTask(Task task, boolean scheduled) {
		String output = "";
		Result result = Result.SUCCESS;
		
		String query = task.getQuery();
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery(query);
			int columnCount = rs.getMetaData().getColumnCount();
			if (columnCount > 0) {
				while (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						String fieldName = rs.getMetaData().getColumnName(i);
						output += "Field = " + fieldName + ", value = " + rs.getString(i) + "\n";
					}
				}

				if (!scheduled) {
					MyNotification.info(output);
				}
			}
			
		} catch (SQLException e1) {
			result = Result.FAILED;
		} finally {
			TaskLog tlog = new TaskLog(null, LocalDateTime.now(), output, result, task);
			tlogService.save(tlog);
		}
	}
}