package com.example.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.Setter;

@Service
public class BaseService<T, ID> {
	
	@Setter
	private JpaRepository<T, ID> repo;		

	public List<T> getAll() {
		return repo.findAll();
	}
	
	public void saveAll(List<T> customers) {	
		repo.saveAll(customers);
	}
	
	public void save(T empl) {
		repo.save(empl);		
	}	
	
	public T get(ID id) {
		return repo.findById(id).orElse(null);
	}
}