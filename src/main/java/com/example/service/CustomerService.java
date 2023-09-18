package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository cstmRepo;	

	public List<Customer> getAll() {
		return cstmRepo.findAll();
	}
	
	public void saveAll(List<Customer> customers) {	
		cstmRepo.saveAll(customers);
	}
	
	public void save(Customer empl) {
		cstmRepo.save(empl);		
	}	
}