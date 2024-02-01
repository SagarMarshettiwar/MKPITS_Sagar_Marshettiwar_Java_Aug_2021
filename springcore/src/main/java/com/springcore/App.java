package com.springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App { 
	  public static void main(String args[]) {
		  System.out.println("hello");
		  ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/Config.xml");
		  Student student1=(Student) context.getBean("student1");
		  System.out.println(student1.toString());
		  Student student2=(Student) context.getBean("student2");
		  System.out.println(student2.toString());
		  Student student3=(Student) context.getBean("student3");
		  System.out.println(student3.toString());
	  } 
    }


/*
 * class Stud{ String name; int age; public Stud(String name, int age) {
 * super(); this.name = name; this.age = age; } public void display() {
 * System.out.println(name+" "+age); }
 * 
 * @Override public String toString() { return "Stud [name=" + name + ", age=" +
 * age + "]"; }
 * 
 * } public class App extends Stud{ String boss; public App(String name, int
 * age,String boss) { super(name, age); // TODO Auto-generated constructor stub
 * this.boss=boss; } public void display() {
 * System.out.println(name+" "+age+" "+boss); }
 * 
 * @Override public String toString() { return "App [boss=" + boss + "]"; }
 * public static void main(String args[]) { App app=new App("sagar", 10,
 * "vipin"); app.display(); System.out.println(app.toString()); Stud stud=new
 * Stud("Sam", 20); System.out.println(stud.toString());
 * 
 * } }
 */