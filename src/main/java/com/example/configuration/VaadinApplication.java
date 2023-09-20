package com.example.configuration;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Theme(value="my-theme", variant=Lumo.DARK)
public class VaadinApplication implements AppShellConfigurator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}