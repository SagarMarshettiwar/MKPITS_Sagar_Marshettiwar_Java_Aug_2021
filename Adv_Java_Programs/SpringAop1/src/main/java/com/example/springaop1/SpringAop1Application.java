package com.example.springaop1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringAop1Application {
    public static void main(String[] args){
        ApplicationContext appcont = new ClassPathXmlApplicationContext("bean.xml");

        Emp eob =appcont.getBean("eob", Emp.class);
        System.out.println(eob.getId()+" "+eob.getName());


        Stud sob =appcont.getBean("stob", Stud.class);
        System.out.println(sob.getId()+" "+sob.getName());
    }
}
