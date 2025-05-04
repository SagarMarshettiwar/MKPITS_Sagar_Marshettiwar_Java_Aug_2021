package com.example.springinheritance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringInheritanceApplication {

    public static void main(String[] args){
        ApplicationContext appcont = new ClassPathXmlApplicationContext("/templates/bean.xml");

        emp e= (emp) appcont.getBean("base");
        System.out.println(e);

        emp e1= (emp) appcont.getBean("ob2");
        System.out.println(e1);
    }
}
