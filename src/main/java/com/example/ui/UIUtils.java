package com.example.ui;

import com.vaadin.flow.component.Component;

public class UIUtils {
	public static void setDescription(Component c, String desc) {
		c.getElement().setProperty("title", desc);
	}
}