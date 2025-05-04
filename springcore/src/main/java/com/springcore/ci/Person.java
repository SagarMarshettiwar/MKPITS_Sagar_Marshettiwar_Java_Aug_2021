package com.springcore.ci;

public class Person {
	private String Pname;
	private int Pid;
	private Certi certi;
	public Person(String pname, int pid, Certi certi) {
		super();
		Pname = pname;
		Pid = pid;
		this.certi = certi;
	}
	@Override
	public String toString() {
		return "Person [Pname=" + Pname + ", Pid=" + Pid + ", certi=" + certi + "]";
	}
	
	
	
}
