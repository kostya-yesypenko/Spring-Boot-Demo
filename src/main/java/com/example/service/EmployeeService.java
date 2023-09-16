package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository emplRepo;	

	public List<Employee> getAll() {
		return emplRepo.findAll();
	}
	
	public void saveAll(List<Employee> employees) {	
		emplRepo.saveAll(employees);
	}
}