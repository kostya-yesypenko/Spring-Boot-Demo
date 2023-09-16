package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.configuration.BeanWirer;
import com.example.service.EmployeeService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route("") // map view to the root
@SuppressWarnings("unchecked")
public class AppLayoutBasic extends AppLayout {

	@Autowired
	private EmployeeService emplService;

	public AppLayoutBasic() {
		BeanWirer.wire(this);
		DrawerToggle toggle = new DrawerToggle();

		emplService.getAll();

		H1 title = new H1("MyApp");
		title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

		Tabs tabs = getTabs();

		addToDrawer(tabs);
		addToNavbar(toggle, title);
		Button btn = new Button("Button");
		btn.addClickListener(click -> {
			if (click.isCtrlKey()) {
				MyNotification.info("You clicked on button");
			}
		});
		addToDrawer(btn);

		var cb = new ComboBox("Countries");
		var countries = Arrays.asList("Angola", "USA", "Ukraine");
		cb.addValueChangeListener(c -> {
			MyNotification.error("You selected " + (String) c.getValue());
		});
		cb.setItems(countries);
		cb.setValue("Ukraine");

		addToDrawer(cb);

	}

	private Tabs getTabs() {
		Tabs tabs = new Tabs();
		Tab t1 = new Tab("Orders");
		t1.addComponentAsFirst(new Icon(VaadinIcon.CART_O));
		t1.setLabel("Orders");

		tabs.addTabAtIndex(0, t1);
		// tabs.addTabAtIndex(1, t2);
		return tabs;
	}
}