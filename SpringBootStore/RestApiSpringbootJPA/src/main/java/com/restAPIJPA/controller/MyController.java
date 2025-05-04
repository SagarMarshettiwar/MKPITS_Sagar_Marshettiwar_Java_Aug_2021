package com.restAPIJPA.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restAPIJPA.entity.Books;
import com.restAPIJPA.services.Services;

@RestController
public class MyController {
	@Autowired
	private Services services;
	
	@GetMapping("/books")
	public List<Books> getBooks() {
		return services.getAll();
	}
	
	@GetMapping("/books/{ID}")
	public Optional<Books> getBooksBYID(@PathVariable("ID") int id) {
		return services.getListById(id);
	}
	
	@PostMapping("/books")
	public Books addBooks(@RequestBody Books b) {
		Books books =this.services.addBooks(b);
		return books;
	}
	
	@DeleteMapping("/books/{ID}")
	public List<Books> deleteBook(@PathVariable("ID")int id) {
		List<Books> deleteBooksBYID = this.services.deleteBooksBYID(id);
		return deleteBooksBYID;
	}
	
	@PutMapping("/books/{ID}")
	public List<Books> updateBooks(@RequestBody Books book,@PathVariable("ID")int id) {
		List<Books> updateBooksById = this.services.updateBooksById(book,id);
		return updateBooksById;
	}
}
