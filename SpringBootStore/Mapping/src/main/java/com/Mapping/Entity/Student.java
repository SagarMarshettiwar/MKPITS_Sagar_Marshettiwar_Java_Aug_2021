package com.Mapping.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String city;
	private String state;
	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Laptop laptop;
	
	public Student() {

	}

	public Student(String name, String city, String state, Laptop laptop) {
		super();
		this.name = name;
		this.city = city;
		this.state = state;
		this.laptop = laptop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Laptop getLaptop() {
		return laptop;
	}

	public void setLaptop(Laptop laptop) {
		this.laptop = laptop;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", city=" + city + ", state=" + state + ", laptop=" + laptop + "]";
	}
}
