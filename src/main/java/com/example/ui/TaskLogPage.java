package com.example.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Customer;
import com.example.domain.Role;
import com.example.domain.Task;
import com.example.domain.TaskLog;
import com.example.enums.Roles;
import com.example.service.CustomerService;
import com.example.service.RoleService;
import com.example.service.TaskLogService;
import com.example.service.TaskService;
import com.example.demo.MainUI;
import com.example.demo.MyNotification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = "TaskLogPage", layout = MainUI.class) // map view to the root
public class TaskLogPage extends BasePage<TaskLog> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskLogService tlogService;
	
	public TaskLogPage() {
		super();
	}

	@Override
	protected void loadGrid() {
		grid.addColumn(e -> e.getId()).setWidth("50px").setHeader("ID").setComparator(TaskLog::getId);
		grid.addColumn(e -> e.getTask().getId()).setWidth("100px").setHeader("Name").setComparator(t -> t.getTask().getId());
		grid.addColumn(e -> e.getDate()).setWidth("300px").setHeader("Execute Date").setComparator(TaskLog::getDate);

		grid.addItemClickListener(it -> {
			//createEditForm(it.getItem());
		});

		grid.setItems(getItems());
	}

	@Override
	protected void createEditForm(Object t) {
//
//		TaskLog item = t == null ? new TaskLog() : (TaskLog) t;
//		editFormLayout.removeAll();
//		this.remove(editFormLayout);
//		editFormLayout.setWidth(null);
//
//		Binder<TaskLog> binder = new Binder<>(TaskLog.class);
//
//		TextField id = new TextField("ID");
//		id.setWidth("50px");
//		id.setReadOnly(true);
//		binder.forField(id).withNullRepresentation("").bind(e -> String.valueOf(e.getId()), null);
//
//		TextField taskId = new TextField("Task ID");
//		taskId.setWidth("100px");
//		binder.forField(taskId).withNullRepresentation("").asRequired("Task ID should not be empty").bind(e -> e.getTaskId().toString(),
//				(e, newTaskId) -> e.setTaskId(newTaskId));
//
//		TextField execDate = new TextField("Execution Date");
//		binder.forField(execDate).withNullRepresentation("").asRequired("Date should not be empty")
//				.bind(e -> e.getDate(), (e, newExecDate) -> e.setDate(newExecDate));
////		queryField.setWidth("300px");
//
//
//		FormLayout formLayout = new FormLayout();
//		formLayout.add(id, taskId, execDate);
//		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
//		formLayout.setWidth("350px");
//
//		Button saveButton = new Button("Save", event -> {
//			try {
//				binder.writeBean(item);
//				tlogService.save(item);
//				grid.setItems(getItems());
//			} catch (ValidationException e) {
//
//			}
//		});
//
//
//		Button reloadButton = new Button("Reload");
//		reloadButton.addClickListener(load -> {
//			binder.readBean(item);
//			createEditForm(item);
//		});
//		editFormLayout.add(formLayout, saveButton, reloadButton);
//
//		this.add(editFormLayout);
//
//		binder.readBean(item);
	}
	
	private List<Task> getTasks() {
		return taskService.getAll().stream().sorted((e1, e2) -> e1.getId().compareTo(e2.getId()))
				.collect(Collectors.toList());
	}

	private List<TaskLog> getItems() {
		return tlogService.getAll().stream().sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
				.collect(Collectors.toList());
	}
}