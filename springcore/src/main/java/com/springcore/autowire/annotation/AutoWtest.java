package com.springcore.autowire.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutoWtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/autowire/annotation/AutoWConfig.xml");
		Employee e=(Employee)context.getBean("employee"); 
		System.out.println(e);
	}
}