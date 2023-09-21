package com.example.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"id", "name"})
public enum Roles {
	MANAGER("Manager", 1), SUPERVISOR("Supervisor",2), INTERN("Intern",3), CLERK("Clerk", 4);
	
	String name;
	int id;
	Roles(String name, int id) {
		this.name = name;
		this.id = id;
	}		
}