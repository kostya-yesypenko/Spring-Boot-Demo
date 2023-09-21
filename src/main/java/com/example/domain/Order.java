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
@Table(name = "[order]")
public class Order {
	@Id 
	@GeneratedValue
	@Column(name ="id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name ="customer_id")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name ="product_id")
	private Product product;	
}