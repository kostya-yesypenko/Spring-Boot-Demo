package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Product;
import com.example.repository.ProductRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService extends BaseService<Product, Integer> {
	
	@Autowired
	private ProductRepository prodRepo;	

	@PostConstruct
	private void init() {
		setRepo(prodRepo);
	}		
}