package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CustomerService extends BaseService<Customer, Integer> {
	
	@Autowired
	private CustomerRepository cstmRepo;
	
	@PostConstruct
	private void init() {
		setRepo(cstmRepo);
	}	
}