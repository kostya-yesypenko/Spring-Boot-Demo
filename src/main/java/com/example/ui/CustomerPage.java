package com.example.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.configuration.BeanWirer;
import com.example.domain.Customer;
import com.example.domain.Role;
import com.example.service.CustomerService;
import com.example.service.RoleService;
import com.example.demo.MainUI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = "CustomerPage", layout = MainUI.class) // map view to the root
public class CustomerPage extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerService emplService;

	@Autowired
	private RoleService roleService;

	Grid<Customer> grid;

	VerticalLayout editFormLayout = new VerticalLayout();

	public CustomerPage() {
		super();
		BeanWirer.wire(this);

		setHeight("100%");

		createContent();
	}

	private void createContent() {
		grid = new Grid<>();
		grid.setItems(getCustomers());
		grid.setSizeFull();
		grid.setWidth("600px");
		grid.addColumn(e -> e.getId()).setWidth("50px").setHeader("ID").setComparator(Customer::getId);
		grid.addColumn(e -> e.getName()).setWidth("300px").setHeader("Name").setComparator(Customer::getName);
		grid.addColumn(e -> e.getRole().getName()).setWidth("250px").setHeader("Role").setComparator(e -> e.getRole().getName());

		grid.setColumnReorderingAllowed(true);

		grid.getColumns().forEach(col -> {
			col.setSortable(true);
		});
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);

		grid.addItemClickListener(employee -> {
			createEditCustomerForm(employee.getItem());
		});
		

		add(grid);
	}

	private void createEditCustomerForm(Customer empl) {
		editFormLayout.removeAll();
		this.remove(editFormLayout);
		editFormLayout.setWidth(null);

		Binder<Customer> binder = new Binder<>(Customer.class);

		TextField id = new TextField("ID");
		id.setReadOnly(true);
		binder.forField(id).bind(e -> String.valueOf(e.getId()), null);

		TextField name = new TextField("Name");
		binder.forField(name).asRequired("Name should not be empty").bind(e -> e.getName(),
				(e, newName) -> e.setName(newName));

		ComboBox<Role> role = new ComboBox<>("Role", getRoles());
		role.setItemLabelGenerator(r -> r.getName());
		binder.forField(role)
				.withValidator(r -> r.getName().length() >= 3, "Role must contain at least three characters")
				.bind(e -> e.getRole(), (e, newRole) -> e.setRole(newRole));

		FormLayout formLayout = new FormLayout();
		formLayout.add(id, name, role);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		formLayout.setWidth("500px");

		Button saveButton = new Button("Save", event -> {
			try {
				binder.writeBean(empl);
				emplService.save(empl);
				grid.setItems(getCustomers());
			} catch (ValidationException e) {

			}
		});
		this.add(saveButton);

		Button reloadButton = new Button("Reload");
		reloadButton.addClickListener(load -> {
			binder.readBean(empl);
		});
		editFormLayout.add(formLayout, saveButton, reloadButton);

		this.add(editFormLayout);

		binder.readBean(empl);
	}

	private List<Role> getRoles() {
		return roleService.getAll();
	}

	private List<Customer> getCustomers() {
		return emplService.getAll().stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
	}
}