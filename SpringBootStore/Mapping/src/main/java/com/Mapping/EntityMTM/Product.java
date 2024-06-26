package com.Mapping.EntityMTM;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Product {
	@Id
	private String pId;
	private String pName;
	
	@ManyToMany(mappedBy = "products",fetch = FetchType.EAGER)
	List<Category>cate=new ArrayList<>();

	public Product() {
	
	}

	public Product(String pId, String pName, List<Category> Category) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.cate = Category;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public List<Category> getProducts() {
		return cate;
	}

	public void setProducts(List<Category> Category) {
		this.cate = Category;
	}
	
	
}
