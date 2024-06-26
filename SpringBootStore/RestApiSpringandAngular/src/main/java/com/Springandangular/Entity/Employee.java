package com.Springandangular.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String FirstName;
	private String LastName;
	private String City;
	
	public Employee() {
	
	}

	public Employee(String firstName, String lastName, String city) {
		super();
		FirstName = firstName;
		LastName = lastName;
		City = city;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	@Override
	public String toString() {
		return "Employee [Id=" + Id + ", FirstName=" + FirstName + ", LastName=" + LastName + ", City=" + City + "]";
	}
	
}
