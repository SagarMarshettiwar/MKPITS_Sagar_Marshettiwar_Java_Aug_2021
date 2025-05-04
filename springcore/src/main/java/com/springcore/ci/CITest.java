package com.springcore.ci;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/ci/CIConfig.xml");
			Person p=(Person) context.getBean("person");
			System.out.println(p.toString());
	}

}
  