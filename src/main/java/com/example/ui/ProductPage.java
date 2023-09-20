package com.example.ui;

import com.example.domain.Customer;
import com.example.domain.Product;
import com.example.domain.Role;
import com.example.service.CustomerService;
import com.example.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.MainUI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route(value = "ProductPage", layout = MainUI.class) // map view to the root
public class ProductPage extends BasePage<Product> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProductService prodService;


	public ProductPage() {
		super();		
	}
	
	@Override
	protected void loadGrid() {
		grid.addColumn(p -> p.getId()).setWidth("50px").setHeader("ID").setComparator(Product::getId);
		grid.addColumn(p -> p.getName()).setWidth("200px").setHeader("Name").setComparator(Product::getName);
		grid.addColumn(p -> p.getQty()).setWidth("100px").setHeader("Quantity").setComparator(Product::getQty);
		grid.addColumn(p -> p.getPrice()).setWidth("100px").setHeader("Price").setComparator(Product::getPrice);
		grid.getColumns().forEach(col -> {
			col.setSortable(true);
		});		

		grid.addItemClickListener(product -> {
			createEditForm(product.getItem());
		});

		grid.setItems(getProducts());
	}

	@Override
	protected void createEditForm(Object prod) {
		
		Product product = prod == null? new Product() : (Product) prod;
		editFormLayout.removeAll();
		this.remove(editFormLayout);
		editFormLayout.setWidth(null);

		Binder<Product> binder = new Binder<>(Product.class);

		TextField id = new TextField("ID");
		id.setReadOnly(true);
		binder.forField(id).bind(p -> String.valueOf(p.getId()), null);

		TextField name = new TextField("Name");
		binder.forField(name).withNullRepresentation("").asRequired("Name should not be empty").bind(p -> p.getName(),
				(p, newName) -> p.setName(newName));
		
		TextField qty = new TextField("Quantity");
		binder.forField(qty).withNullRepresentation("").asRequired("Quantity should not be empty").bind(p -> String.valueOf(p.getQty()),
				(p, newQty) -> p.setQty(Integer.valueOf(newQty)));
		
		TextField price = new TextField("Price");
		binder.forField(price).withNullRepresentation("").asRequired("Price should not be empty").bind(p -> String.valueOf(p.getPrice()),
				(p, newPrice) -> p.setPrice(Double.valueOf(newPrice)));


		FormLayout formLayout = new FormLayout();
		formLayout.add(id, name, qty, price);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		formLayout.setWidth("500px");

		Button saveButton = new Button("Save", event -> {
			try {
				binder.writeBean(product);
				prodService.save(product);
				grid.setItems(getProducts());
			} catch (ValidationException e) {

			}
		});
		this.add(saveButton);

		Button reloadButton = new Button("Reload");
		reloadButton.addClickListener(load -> {
			binder.readBean(product);
		});
		editFormLayout.add(formLayout, saveButton, reloadButton);

		this.add(editFormLayout);

		binder.readBean(product);
	}

	private List<Product> getProducts() {
		return prodService.getAll().stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
				.collect(Collectors.toList());
	}
}