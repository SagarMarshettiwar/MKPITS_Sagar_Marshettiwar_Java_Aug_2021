package com.springcore.autowire.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Employee {
	@Autowired
	 @Qualifier("add1")
	private Address address;

	public Address getAddress() {
		return address;
	}

	/* @Autowired */
	public void setAddress(Address address) {
		System.out.println("setter method");
		this.address = address;
	}

	/* @Autowired */
	public Employee(Address address) {
		super();
		this.address = address;
		System.out.println("inside constructor");
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Employee [address=" + address + "]";
	}
	
	
}
