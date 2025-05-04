package com.Springandangular.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Springandangular.Entity.Employee;
import com.Springandangular.repository.Repo;

@Component
public class Service {
	@Autowired
	private Repo repo;

	public List<Employee> getEmployees(){
		List<Employee> all = (List<Employee>) repo.findAll();
		return all;
	}
	
	public Optional<Employee> getEmployeebyid(int id) {
		Optional<Employee> optional = repo.findById(id);
		return optional;
	}
}