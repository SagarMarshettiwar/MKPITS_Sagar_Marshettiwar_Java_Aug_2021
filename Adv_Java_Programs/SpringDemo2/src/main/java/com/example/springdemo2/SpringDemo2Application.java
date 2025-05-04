package com.example.springdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringDemo2Application {

    public static void main(String[] args){
        ApplicationContext appcont = new ClassPathXmlApplicationContext("/templates/bean.xml");

        /*Employee2 emp2 = (Employee2) appcont.getBean("Emp2");
        emp2.showdetail();

        Account2 aob2 = (Account2) appcont.getBean("Aob2");
        aob2.tran1();*/

//        Employee3 emp3 = (Employee3) appcont.getBean("emp3");
//        emp3.display();
//
//        Account3 aob3 = (Account3) appcont.getBean("Aob3");
//        aob3.Ashow();

        Employee3 emp3 = (Employee3) appcont.getBean("emp");
        emp3.display();

        Account3 aob3 = (Account3) appcont.getBean("Aob4");
        aob3.Ashow();

    }
}
