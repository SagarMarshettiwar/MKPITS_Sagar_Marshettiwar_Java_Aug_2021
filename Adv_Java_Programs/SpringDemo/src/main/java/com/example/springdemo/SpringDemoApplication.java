package com.example.springdemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemoApplication {

    public static void main(String[] args){
        ApplicationContext appcont = new ClassPathXmlApplicationContext("/templates/bean.xml");

        /*Employee emp = (Employee) appcont.getBean("Emp");
        emp.Display();

        Account aob = (Account) appcont.getBean("Aob");
        aob.transaction();




        Employee1 emp1 = (Employee1) appcont.getBean("Emp1");
        emp1.show();

        Account1 aob1 = (Account1) appcont.getBean("Aob1");
        aob1.tran();
*/



        Employee2 emp2 = (Employee2) appcont.getBean("Emp2");
        emp2.showdetail();

        Account2 aob2 = (Account2) appcont.getBean("Aob2");
        aob2.tran1();
    }
}
