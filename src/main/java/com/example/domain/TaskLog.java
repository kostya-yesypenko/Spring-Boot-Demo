package com.example.domain;

import java.time.LocalDateTime;

import com.example.enums.Result;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "task_log")
@NoArgsConstructor
@AllArgsConstructor
public class TaskLog {	

	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Integer id;
	@Column(name ="exec_date")
	private LocalDateTime date;
	@Column(name ="output")
	private String output;
	@Column(name ="result")	
	@Enumerated(EnumType.STRING)
	private Result result;
	
	@OneToOne
	@JoinColumn(name ="task_id")
	private Task task;
}