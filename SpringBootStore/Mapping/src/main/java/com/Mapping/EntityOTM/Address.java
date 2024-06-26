package com.Mapping.EntityOTM;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int addressId;
	private String street;
	private String city;
	private String country;
	@ManyToOne
	@JoinColumn(name = "employee_id") 
	 @JsonBackReference
	Employee employeeid;
	public Address() {
		
	}
	public Address( String street, String city, String country, Employee employeeid) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
		this.employeeid = employeeid;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Employee getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(Employee employeeid) {
		this.employeeid = employeeid;
	}
	
}
