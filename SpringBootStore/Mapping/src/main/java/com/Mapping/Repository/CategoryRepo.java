package com.Mapping.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mapping.EntityMTM.Category;

public interface CategoryRepo extends JpaRepository<Category, String>{

}
