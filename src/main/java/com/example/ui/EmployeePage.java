package com.example.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.configuration.BeanWirer;
import com.example.domain.Employee;
import com.example.service.EmployeeService;
import com.example.demo.MainUI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = "EmployeePage", layout = MainUI.class) // map view to the root
@SuppressWarnings("unchecked")
public class EmployeePage extends HorizontalLayout {
	@Autowired
	private EmployeeService emplService;
	
	FormLayout formLayout = new FormLayout();

	public EmployeePage() {
		super();
		BeanWirer.wire(this);

		setSizeFull();

		createContent();
	}

	private void createContent() {
		Grid<Employee> grid = new Grid<>();
		grid.setItems(getEmployees());
		grid.setSizeFull();
		grid.setWidth("600px");
		grid.addColumn(e -> e.getId()).setWidth("50px").setHeader("ID").setComparator(Employee::getId);
		grid.addColumn(e -> e.getName()).setWidth("300px").setHeader("Name").setComparator(Employee::getName);
		grid.addColumn(e -> e.getRole()).setWidth("250px").setHeader("Role").setComparator(Employee::getRole);

		grid.setColumnReorderingAllowed(true);

		grid.getColumns().forEach(col -> {
			col.setSortable(true);
		});
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);

		grid.addItemDoubleClickListener(employee -> {
			createEditEmployeeForm(employee.getItem());
		});

		add(grid);
	}

	private void createEditEmployeeForm(Employee e) {
		this.remove(formLayout);
		
		TextField id = new TextField("ID");
		id.setValue(String.valueOf(e.getId()));
		id.setReadOnly(true);

		TextField name = new TextField("Name");
		name.setValue(e.getName());

		TextField role = new TextField("Role");
		role.setValue(e.getRole());

		formLayout = new FormLayout();
		formLayout.add(id, name, role);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		this.addAndExpand(formLayout);
	}

	private List<Employee> getEmployees() {
		return emplService.getAll().stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
	}
}