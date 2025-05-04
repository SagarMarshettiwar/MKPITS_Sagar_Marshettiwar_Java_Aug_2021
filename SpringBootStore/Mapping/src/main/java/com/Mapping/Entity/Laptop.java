package com.Mapping.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Laptop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lpid;
	private String lname;
	private String lmodel;
	@OneToOne
	@JsonBackReference
	private Student student;
	
	public Laptop() {

	}

	public Laptop(String lname, String lmodel, Student student) {
		super();
		this.lname = lname;
		this.lmodel = lmodel;
		this.student = student;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLmodel() {
		return lmodel;
	}

	public void setLmodel(String lmodel) {
		this.lmodel = lmodel;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "Laptop [lname=" + lname + ", lmodel=" + lmodel + ", student=" + student + "]";
	}
	
}
