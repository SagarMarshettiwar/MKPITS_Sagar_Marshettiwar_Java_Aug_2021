package com.springcore.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext Context=new ClassPathXmlApplicationContext("com/springcore/spel/SpelConfig.xml");
		Student s=(Student) Context.getBean("student");
		System.out.println(s);
		
		
		SpelExpressionParser eparser=new SpelExpressionParser();
		Expression expression=eparser.parseExpression("50+50");
		System.out.println(expression.getValue());
	}
}