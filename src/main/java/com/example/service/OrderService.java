package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

import jakarta.annotation.PostConstruct;

@Service
public class OrderService extends BaseService<Order, Integer> {
	
	@Autowired
	private OrderRepository orderRepo;	
	
	@PostConstruct
	private void init() {
		setRepo(orderRepo);
	}	
}