package com.example.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Customer;
import com.example.domain.Role;
import com.example.enums.Roles;
import com.example.service.CustomerService;
import com.example.service.RoleService;
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

@Route(value = "CustomerPage", layout = MainUI.class) // map view to the root
public class CustomerPage extends BasePage<Customer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RoleService roleService;

	public CustomerPage() {
		super("Customers");		
	}
	
	@Override
	protected void loadGrid() {
		grid.addColumn(e -> e.getId()).setWidth("50px").setHeader("ID").setComparator(Customer::getId);
		grid.addColumn(e -> e.getName()).setWidth("300px").setHeader("Name").setComparator(Customer::getName);
		grid.addColumn(e -> e.getRole().getName()).setWidth("250px").setHeader("Role").setComparator(e -> e.getRole().getName());
		grid.getColumns().forEach(col -> {
			col.setSortable(true);
		});
		
		grid.addItemClickListener(item -> {
			createEditForm(item.getItem());
		});
		
		grid.setItems(getItems());
	}

	@Override
	protected void createEditForm(Object it) {
		
		Customer item = it == null? new Customer() : (Customer) it;
		editFormLayout.removeAll();
		this.remove(editFormLayout);
		editFormLayout.setWidth(null);

		Binder<Customer> binder = new Binder<>(Customer.class);

		TextField id = new TextField("ID");
		id.setReadOnly(true);
		binder.forField(id).withNullRepresentation("").bind(e -> String.valueOf(e.getId()), null);

		TextField name = new TextField("Name");
		binder.forField(name).withNullRepresentation("").asRequired("Name should not be empty").bind(e -> e.getName(),
				(e, newName) -> e.setName(newName));

		ComboBox<Role> role = new ComboBox<>("Role", getRoles());
		role.addValueChangeListener(change->{
			if (Roles.SUPERVISOR.getName().equals(change.getValue().getName())){
				MyNotification.error("CAUTION: You Selected Supervisor role!");
			}
		});
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
				binder.writeBean(item);
				customerService.save(item);
				grid.setItems(getItems());
			} catch (ValidationException e) {

			}
		});
		this.add(saveButton);

		Button reloadButton = new Button("Reload");
		reloadButton.addClickListener(load -> {
			binder.readBean(item);
		});
		editFormLayout.add(formLayout, saveButton, reloadButton);

		this.add(editFormLayout);

		binder.readBean(item);
	}

	private List<Role> getRoles() {
		return roleService.getAll();
	}

	private List<Customer> getItems() {
		return customerService.getAll().stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
	}
}