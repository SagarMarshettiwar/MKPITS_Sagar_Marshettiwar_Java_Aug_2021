package com.springcore.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Dhokla {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dhokla() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Dhokla [name=" + name + "]";
	}
	
	@PostConstruct
	public void start() {
		System.out.println("Starting");
	}
	@PreDestroy
	public void end() {
		System.out.println("Ending");
	}
}
