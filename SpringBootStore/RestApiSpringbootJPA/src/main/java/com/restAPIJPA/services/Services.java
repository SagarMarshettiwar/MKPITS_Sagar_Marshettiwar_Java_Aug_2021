package com.restAPIJPA.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restAPIJPA.entity.Books;
import com.restAPIJPA.repository.Repo;

@Component
public class Services {
	@Autowired
	private Repo repo;
	
	public List<Books> getAll(){
		List<Books> all = (List<Books>)this.repo.findAll();
		return all;
	}
	
	public Optional<Books> getListById(int id) {
		Optional<Books> books = repo.findById(id);
		return books;
		
	}
	
	public Books addBooks(Books b) {
		 Books books = repo.save(b);
		return books;
		
	}
	
	public List<Books> deleteBooksBYID(int id) {
		repo.deleteById(id);
		List<Books> all = (List<Books>) this.repo.findAll();
		return all;
	}
	
	public List<Books> updateBooksById(Books book,int id) {
		repo.save(book);
		List<Books> all = (List<Books>) this.repo.findAll();
		return all;
		
	}
}
