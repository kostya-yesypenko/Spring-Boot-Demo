package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	}