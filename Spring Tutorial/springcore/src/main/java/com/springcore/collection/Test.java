package com.springcore.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String args[]) {
		ApplicationContext context =new ClassPathXmlApplicationContext("com/springcore/collection/CConfig.xml");
		Emp e=(Emp) context.getBean("emp1");
		System.out.println(e);
		System.out.println(e.getName());
		System.out.println(e.getPhones());
		System.out.println(e.getAddresses());
		System.out.println(e.getCourss());
		System.out.println(e.getProp());
	}
}