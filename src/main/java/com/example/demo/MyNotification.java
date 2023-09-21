package com.example.demo;

import java.util.Arrays;

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
public class MyNotification{
	public static void info(String msg) {
		Notification notification = createNotification(msg, Position.TOP_END, 5000);
		notification.addClassName("info-notification");	
		notification.open();
	}
	public static void error(String msg) {
		Notification notification = createNotification(msg, Position.BOTTOM_CENTER, 0);
		notification.addClassName("error-notification");

		notification.getElement().addEventListener("click", click->{
			notification.close();
		});
		notification.open();
	}
	
	public static void warn(String msg) {
		Notification notification = createNotification(msg, Position.TOP_END, 0);
		notification.addClassName("warn-notification");	
		notification.open();
	}
	
	private static Notification createNotification(String msg, Position pos, int duration) {
		Notification notification = new Notification(msg, duration, Position.TOP_END);	
		return notification;
	}
}