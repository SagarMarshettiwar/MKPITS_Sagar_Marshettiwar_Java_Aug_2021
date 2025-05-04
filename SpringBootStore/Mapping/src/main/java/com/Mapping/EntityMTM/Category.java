package com.Mapping.EntityMTM;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Category {
	@Id
	private String cID;
	private String cName;
	@ManyToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER)
	List<Product>products=new ArrayList<>();
	
	public Category() {
		
	}

	public Category(String cID, String cName, List<Product> products) {
		super();
		this.cID = cID;
		this.cName = cName;
		this.products = products;
	}

	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
}
