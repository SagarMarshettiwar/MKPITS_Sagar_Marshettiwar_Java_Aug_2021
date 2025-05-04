package com.springcore.stereotype;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
@Component("ob")
@Scope("prototype")
public class Student {
	@Value("Sagar")
	private String Fname;
	@Value("Marshettiwar")
	private String Lname;
	@Value("#{linklist}")
	private List<String> Address;
	
	public List<String> getAddress() {
		return Address;
	}
	public void setAddress(List<String> address) {
		Address = address;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getLname() {
		return Lname;
	}
	public void setLname(String lname) {
		Lname = lname;
	}
	@Override
	public String toString() {
		return "Student [Fname=" + Fname + ", Lname=" + Lname + ", Address=" + Address + "]";
	}
	
}

