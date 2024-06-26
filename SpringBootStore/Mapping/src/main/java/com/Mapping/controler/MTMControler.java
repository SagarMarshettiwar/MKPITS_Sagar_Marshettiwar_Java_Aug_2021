package com.Mapping.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Mapping.EntityMTM.Category;
import com.Mapping.EntityMTM.Product;
import com.Mapping.Repository.CategoryRepo;

@RestController
public class MTMControler {
	
	@Autowired
	CategoryRepo cRepo;
	List<Product> pro;
	
	@PostMapping("/Category")
	public Category addCategory(@RequestBody Category c) {
	    return cRepo.save(c);
	} 
}
