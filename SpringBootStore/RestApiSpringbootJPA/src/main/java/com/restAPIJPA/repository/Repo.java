package com.restAPIJPA.repository;

import org.springframework.data.repository.CrudRepository;
import com.restAPIJPA.entity.Books;

public interface Repo extends CrudRepository<Books,Integer> {
}
