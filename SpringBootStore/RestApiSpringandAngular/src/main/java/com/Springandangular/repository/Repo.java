package com.Springandangular.repository;

import org.springframework.data.repository.CrudRepository;

import com.Springandangular.Entity.Employee;


public interface Repo extends CrudRepository<Employee,Integer> {

}
