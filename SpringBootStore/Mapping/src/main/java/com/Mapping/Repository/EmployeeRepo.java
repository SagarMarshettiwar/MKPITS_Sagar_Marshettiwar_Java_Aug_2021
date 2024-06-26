package com.Mapping.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Mapping.EntityOTM.Employee;

public interface EmployeeRepo extends  JpaRepository<Employee,Integer>{

}
