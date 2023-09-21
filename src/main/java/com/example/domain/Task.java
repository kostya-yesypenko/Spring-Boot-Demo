package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "task")
public class Task {
	@Id 
	@GeneratedValue
	@Column(name ="id")
	private Integer id;
	@Column(name ="name")
	private String name;
	@Column(name ="query")
	private String query;
	@Column(name ="schedule")
	private String schedule;
}