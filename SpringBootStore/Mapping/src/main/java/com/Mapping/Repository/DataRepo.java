package com.Mapping.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mapping.Entity.Student;

public interface DataRepo extends JpaRepository<Student,Integer> {

}
