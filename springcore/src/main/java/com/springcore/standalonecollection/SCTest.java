package com.springcore.standalonecollection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SCTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/standalonecollection/SCConfig.xml");
		Person p=(Person)context.getBean("p1");
		System.out.println(p.getFriends()); 
		System.out.println(p.getFriends().getClass().getName());
		System.out.println("==================================================================");
		System.out.println(p.getFeestructor()); 
		System.out.println(p.getFeestructor().getClass().getName());
		System.out.println(p.getFeestructor().get("c"));
		System.out.println("==================================================================");
		System.out.println(p.getProperty()); 
		System.out.println(p.getProperty().getClass().getName());
		
	}

}
