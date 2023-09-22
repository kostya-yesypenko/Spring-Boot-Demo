package com.example.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Task;
import com.example.service.CustomerService;
import com.example.service.TaskService;
import com.example.demo.MainUI;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = "TaskPage", layout = MainUI.class) // map view to the root
public class TaskPage extends BasePage<Task> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TaskService taskService;

	public TaskPage() {
		super();
	}

	@Override
	protected void loadGrid() {
		grid.addColumn(e -> e.getId()).setWidth("50px").setHeader("ID").setComparator(Task::getId);
		grid.addColumn(e -> e.getName()).setWidth("100px").setHeader("Name").setComparator(Task::getName);
		grid.addColumn(e -> e.getQuery()).setWidth("300px").setHeader("Query").setComparator(Task::getQuery);
		grid.addColumn(e -> e.getSchedule()).setWidth("50px").setHeader("Schedule").setComparator(Task::getSchedule);

		grid.addItemClickListener(it -> {
			createEditForm(it.getItem());
		});

		grid.setItems(getItems());
	}

	@Override
	protected void createEditForm(Object t) {

		Task item = t == null ? new Task() : (Task) t;
		editFormLayout.removeAll();
		this.remove(editFormLayout);
		editFormLayout.setWidth(null);

		Binder<Task> binder = new Binder<>(Task.class);

		TextField id = new TextField("ID");
		id.setWidth("50px");
		id.setReadOnly(true);
		binder.forField(id).withNullRepresentation("").bind(e -> String.valueOf(e.getId()), null);

		TextField name = new TextField("Name");
		name.setWidth("100px");
		binder.forField(name).withNullRepresentation("").asRequired("Name should not be empty").bind(e -> e.getName(),
				(e, newName) -> e.setName(newName));

		TextField queryField = new TextField("Query");
		binder.forField(queryField).withNullRepresentation("").asRequired("Query should not be empty")
				.bind(e -> e.getQuery(), (e, newQuery) -> e.setQuery(newQuery));
		queryField.setWidth("300px");

		TextField schedule = new TextField("Schedule");
		binder.forField(schedule).withNullRepresentation("").asRequired("Schedule should not be empty")
				.bind(e -> e.getSchedule(), (e, newSchedule) -> e.setSchedule(newSchedule));
		schedule.setWidth("50px");

		FormLayout formLayout = new FormLayout();
		formLayout.add(id, name, queryField, schedule);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		formLayout.setWidth("350px");

		Button saveButton = new Button("Save", event -> {
			try {
				binder.writeBean(item);
				taskService.save(item);
				grid.setItems(getItems());
			} catch (ValidationException e) {

			}
		});

		Button runButton = new Button("Run!", event -> {
			UI.getCurrent().access(() -> {
				taskService.runTask(item, false);
			});
		});

		Button reloadButton = new Button("Reload");
		reloadButton.addClickListener(load -> {
			binder.readBean(item);
			createEditForm(item);
		});
		editFormLayout.add(formLayout, saveButton, reloadButton, runButton);

		this.add(editFormLayout);

		binder.readBean(item);
	}

	private List<Task> getItems() {
		return taskService.getAll().stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
	}
}