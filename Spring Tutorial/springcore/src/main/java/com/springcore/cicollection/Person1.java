package com.springcore.cicollection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Person1 {
	String name;
	List<String> phone;
	Set<String> Address;
	Map<String, String> course;
	public Person1(String name, List<String> phone, Set<String> address, Map<String, String> course) {
		super();
		this.name = name;
		this.phone = phone;
		Address = address;
		this.course = course;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", phone=" + phone + ", Address=" + Address + ", course=" + course + "]";
	}
	
}
