package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.configuration.BeanWirer;
import com.example.ui.CustomerPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("demo") // map view to the root
@SuppressWarnings("unchecked")
@CssImport("./styles.css")
public class MainUI extends AppLayout {


	public MainUI() {
		BeanWirer.wire(this);
		DrawerToggle toggle = new DrawerToggle();

		H1 title = new H1("MyApp");
		title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

		Tabs tabs = getTabs();

		addToDrawer(tabs);
		addToNavbar(toggle, title);
	}

	private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.DASHBOARD, "Dashboard", null),
                createTab(VaadinIcon.CART, "Orders", null),
                createTab(VaadinIcon.USER_HEART, "Customers", CustomerPage.class),
                createTab(VaadinIcon.PACKAGE, "Products", null),
                createTab(VaadinIcon.RECORDS, "Documents", null),
                createTab(VaadinIcon.LIST, "Tasks", null),
                createTab(VaadinIcon.CHART, "Analytics", null));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> clazz) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        if (clazz !=null) {
        	link.setRoute(clazz);
        }
        link.add(icon, new Span(viewName));
        // Demo has no routes
        link.setTabIndex(-1);

        return new Tab(link);
    }

}