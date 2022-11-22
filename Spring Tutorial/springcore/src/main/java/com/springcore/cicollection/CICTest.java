package com.springcore.cicollection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CICTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/cicollection/CICConfig.xml");
		Person1 p= (Person1) context.getBean("per");
		System.out.println(p);
	}

}
