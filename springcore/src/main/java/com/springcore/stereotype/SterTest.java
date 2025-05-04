package com.springcore.stereotype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SterTest {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("com/springcore/stereotype/StereoConfig.xml");
		Student s=context.getBean("ob",Student.class);
		System.out.println(s);
		System.out.println(s.hashCode());
		System.out.println("==============================================================");
		Student s1=context.getBean("ob",Student.class);
		System.out.println(s1.hashCode());
		System.out.println("==============================================================");
		Teacher t=context.getBean("ob2",Teacher.class);
		System.out.println(t);
		System.out.println(t.hashCode());
		System.out.println("==============================================================");
		Teacher t2=context.getBean("ob2",Teacher.class);
		System.out.println(t2.hashCode());
	}

}