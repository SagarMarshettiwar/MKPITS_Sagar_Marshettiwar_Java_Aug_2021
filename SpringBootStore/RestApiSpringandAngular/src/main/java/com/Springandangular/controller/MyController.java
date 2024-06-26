package com.Springandangular.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.Springandangular.Entity.Employee;
import com.Springandangular.service.Service;

@RestController
public class MyController {
	@Autowired
	private Service service;
	@GetMapping("/employee")
	public List<Employee> getEmp(){
		return service.getEmployees();	
	}
	
	@GetMapping("/employee/{ID}")
	public Optional<Employee> getEmpid(@PathVariable("ID") int id){
		return service.getEmployeebyid(id);
	}
}
