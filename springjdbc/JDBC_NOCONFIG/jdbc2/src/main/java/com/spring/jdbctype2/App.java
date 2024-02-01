package com.spring.jdbctype2;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.jdbctype2.Dao.StudentDao;
import com.spring.jdbctype2.entities.Student;

public class App 
{
    public static void main( String[] args ) {
    System.out.println( "Program Started..........");
    ApplicationContext context=new AnnotationConfigApplicationContext(JdbcConfig.class);
    StudentDao studentDao = context.getBean("studdao",StudentDao.class);
    
//    Student s=new Student();
//    s.setId(4);
//    s.setName("ram");
//    s.setCity("newyork");
//    int result = studentDao.insert(s);
//    System.out.println( "Record Affected "+result);

//    Student s1=new Student();
//    s1.setId(4);
//    s1.setName("ram");
//    s1.setCity("newyork");
//    int result = studentDao.update(s1);
//    System.out.println( "Record updated "+result);
    
//    Student s2=new Student(); s2.setId(4); 
//    int result = studentDao.delete(s2);
//    System.out.println( "Record deleted "+result);
    
//    Student student = studentDao.getStudent(4);
//    System.out.println( "Data Found "+student);
    
    List<Student> studentlist = studentDao.getStudentlist();
    for(Student s:studentlist) {
    	System.out.println( "Data Found list "+s);
    }
    
    
//    JdbcTemplate jdbcTemplate=context.getBean("JdbcTemplate",JdbcTemplate.class);
//
//    String query="Insert into Student (id,name,city) values (?,?,?)";
//    int result = jdbcTemplate.update(query,4,"rohit","south");
//    System.out.println( "Record Affected "+result);
}
}
