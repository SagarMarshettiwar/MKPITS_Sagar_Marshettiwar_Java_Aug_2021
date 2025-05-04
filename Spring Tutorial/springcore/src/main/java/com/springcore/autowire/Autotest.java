package com.springcore.autowire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Autotest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/autowire/AutoConfig.xml");
		
		Employee e=(Employee)context.getBean("employee"); 
		System.out.println(e);
		Employee e1=(Employee)context.getBean("employee1"); 
		System.out.println(e1);
		Employee e2=(Employee)context.getBean("employee2");
		System.out.println(e2);
	}

}
