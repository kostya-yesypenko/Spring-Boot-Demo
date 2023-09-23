package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.example.configuration.BeanWirer;
import com.example.ui.*;
import com.example.ui.OrderPage;
import com.example.ui.ProductPage;
import com.example.ui.TaskPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;

import com.vaadin.flow.component.checkbox.Checkbox;

@Route("demo") // map view to the root
@SuppressWarnings("unchecked")
public class MainUI extends AppLayout {
	@Value("${default.user:dmytro}")
	private String username;
	
	public static Map<String, Refreshable> pagesByName = new HashMap<>();

	public MainUI() {
		BeanWirer.wire(this);
		setupErrorHandler();
		DrawerToggle toggle = new DrawerToggle();

		H1 user = new H1("Hi, " + username);
		user.getStyle().set("font-size", "var(--lumo-font-size-l)").set("font-weight", "bold");

		Tabs tabs = getTabs();

		addToDrawer(createToggleThemeButton(), tabs);
		addToNavbar(toggle, user);
	}

	private Tabs getTabs() {
		Tabs tabs = new Tabs();
		tabs.add(createTab(VaadinIcon.DASHBOARD, "Dashboard", null),
				createTab(VaadinIcon.CART, "Orders", OrderPage.class),
				createTab(VaadinIcon.USER_HEART, "Customers", CustomerPage.class),
				createTab(VaadinIcon.PACKAGE, "Products", ProductPage.class),
				createTab(VaadinIcon.RECORDS, "Documents", null), createTab(VaadinIcon.LIST, "Tasks", TaskPage.class),
				createTab(VaadinIcon.DATE_INPUT, "Task Log", TaskLogPage.class));
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		
		return tabs;
	}

	private Tab createTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> clazz) {
		Icon icon = viewIcon.create();
		icon.getStyle().set("box-sizing", "border-box").set("margin-inline-end", "var(--lumo-space-m)")
				.set("margin-inline-start", "var(--lumo-space-xs)").set("padding", "var(--lumo-space-xs)");

		RouterLink link = new RouterLink();
		if (clazz != null) {
			link.setRoute(clazz);
		}
		link.add(icon, new Span(viewName));
		// Demo has no routes
		link.setTabIndex(-1);

		Tab tab = new Tab(link);

		tab.getElement().addEventListener("click", click -> {
			Refreshable page = pagesByName.get(viewName);
			
			if (page !=null) {
				page.refresh();
			}
		});

		return tab;
	}

	public Component createToggleThemeButton() {
		Checkbox toggleButton = new Checkbox("Dark/Light", click -> {
			ThemeList themeList = this.getElement().getThemeList();

			if (themeList.contains(Lumo.DARK)) {
				themeList.remove(Lumo.DARK);
			} else {
				themeList.add(Lumo.DARK);
			}
		});

		return toggleButton;
	}

	private void setupErrorHandler() {
		VaadinSession.getCurrent().setErrorHandler(error -> {
			if (UI.getCurrent() != null) {
				UI.getCurrent().access(() -> {
					Notification.show("An internal error has occurred: " + error.getThrowable().getMessage());
				});
			}
		});
	}
}