package com.example.ui;

import com.example.configuration.BeanWirer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BasePage<T> extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	Grid<T> grid;

	VerticalLayout editFormLayout = new VerticalLayout();

	public BasePage() {
		super();
		BeanWirer.wire(this);

		setHeight("100%");
		
		setPadding(false);

		createContent();
	}

	private void createContent() {
		createGrid();		
	}

	private void createGrid() {
		grid = new Grid<>();
		grid.setSizeFull();

		grid.setColumnReorderingAllowed(true);

		
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);		

		VerticalLayout gridAndButtonLayout = new VerticalLayout(createAddNewButton(), grid);
		gridAndButtonLayout.setPadding(false);
		gridAndButtonLayout.setSpacing(false);
		add(gridAndButtonLayout);
		
		loadGrid();
	}
	
	private Component createAddNewButton() {
		Button btn = new Button(new Icon(VaadinIcon.PLUS_CIRCLE));
		btn.addThemeVariants(ButtonVariant.LUMO_ICON);
		
		UIUtils.setDescription(btn, "Add new");
		btn.addClickListener(click->{
			createEditForm(null);
		});
		return btn;
	}

	protected abstract void loadGrid();

	protected abstract void createEditForm(Object empl);
}