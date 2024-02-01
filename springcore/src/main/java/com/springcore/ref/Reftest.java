package com.springcore.ref;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Reftest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/ref/RefConfig.xml");
			A a=(A) context.getBean("aref");
			System.out.println(a);	
			System.out.println(a.toString());	
			System.out.println(a.getX());
			System.out.println(a.getOb().getY());
	}

}
