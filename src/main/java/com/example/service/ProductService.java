package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.domain.Customer;
import com.example.domain.Product;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository prodRepo;	

	public List<Product> getAll() {
		return prodRepo.findAll();
	}
	
	public void saveAll(List<Product> products) {	
		prodRepo.saveAll(products);
	}
	
	public void save(Product prod) {
		prodRepo.save(prod);		
	}	
}