package com.Mapping.EntityOTM;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;
	private String name;
	private String about;
	@OneToMany(mappedBy = "employeeid", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<Address> addresslist;
	
	public Employee() {
		
	}

	public Employee(String name, String about, List<Address> addresslist) {
		super();
		this.name = name;
		this.about = about;
		this.addresslist = addresslist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Address> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<Address> addresslist) {
		this.addresslist = addresslist;
	}
	
}
