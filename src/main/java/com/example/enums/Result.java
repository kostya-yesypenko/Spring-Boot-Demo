package com.example.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"name"})
public enum Result {
	FAILED("Failed"), SUCCESS("Success");
	
	String name;
	Result(String name) {
		this.name = name;
	}		
}