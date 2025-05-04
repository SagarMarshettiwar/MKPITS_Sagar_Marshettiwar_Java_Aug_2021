package com.example.springjdbc;

import com.example.springjdbc.dao.Studdaoimpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;


public class SpringJdbcApplication {

    public static void main(String[] args) {
        ApplicationContext ctx= new ClassPathXmlApplicationContext("bean.xml");
//        try{
//            DriverManagerDataSource  source = ctx.getBean("datasource1", DriverManagerDataSource.class);
//            Connection connection=source.getConnection();
//            System.out.println("connect");
//        }catch(Exception e){
//            System.out.println(e);
//        }




//        JdbcTemplate template = ctx.getBean("jdbctemp", JdbcTemplate.class);
//        String query="insert into sagar.students (id,name,job) values (?,?,?)";
//        int sts=template.update(query,4,"aman","sql");
//        System.out.println("recotd "+sts);


        Studdaoimpl  std = ctx.getBean("std", Studdaoimpl.class);

        Stud ob=new Stud();
//        ob.setId(2);
//        ob.setName("lol");
//        ob.setJob("hehe");

        std.displaybyid(3);


    }
}
