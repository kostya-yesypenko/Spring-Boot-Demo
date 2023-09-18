package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "customer")
public class Customer {
	@Id 
	@GeneratedValue
	@Column(name ="id")
	private int id;
	@Column(name ="name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;
}