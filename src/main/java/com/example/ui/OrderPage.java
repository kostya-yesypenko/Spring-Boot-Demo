package com.example.ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.domain.Product;
import com.example.service.CustomerService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.demo.MainUI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = "OrderPage", layout = MainUI.class) // map view to the root
public class OrderPage extends BasePage<Order> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	public OrderPage() {
		super("Orders");
	}

	@Override
	protected void loadGrid() {
		grid.addColumn(e -> e.getId()).setWidth("50px").setHeader("ID").setComparator(Order::getId);
		grid.addColumn(e -> e.getCustomer().getName()).setWidth("300px").setHeader("Customer Name");
		grid.addColumn(e -> e.getProduct().getName()).setWidth("250px").setHeader("Product");
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

		Order item = it == null ? new Order() : (Order) it;
		editFormLayout.removeAll();
		this.remove(editFormLayout);
		editFormLayout.setWidth(null);

		Binder<Order> binder = new Binder<>(Order.class);

		TextField id = new TextField("ID");
		id.setReadOnly(true);
		binder.forField(id).withNullRepresentation("").bind(e -> String.valueOf(e.getId()), null);

		ComboBox<Customer> customers = new ComboBox<>("Customer", getCustomers());
		customers.setItemLabelGenerator(r -> r.getName());

		binder.forField(customers).asRequired("Customer should not be empty").bind(e -> e.getCustomer(),
				(e, newCustomer) -> e.setCustomer(newCustomer));

		ComboBox<Product> products = new ComboBox<>("Product", getProducts());
		products.setItemLabelGenerator(r -> r.getName());
		binder.forField(products).asRequired("Product should not be empty").bind(e -> e.getProduct(),
				(e, newProduct) -> e.setProduct(newProduct));

		FormLayout formLayout = new FormLayout();
		formLayout.add(id, customers, products);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		formLayout.setWidth("500px");

		Button saveButton = new Button("Save", event -> {
			try {
				binder.writeBean(item);
				orderService.save(item);
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

	private List<Product> getProducts() {
		return productService.getAll().stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
	}

	private List<Customer> getCustomers() {
		return customerService.getAll().stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
	}

	private List<Order> getItems() {
		return orderService.getAll().stream().sorted((e1, e2) -> e1.getId().compareTo(e2.getId()))
				.collect(Collectors.toList());
	}
}