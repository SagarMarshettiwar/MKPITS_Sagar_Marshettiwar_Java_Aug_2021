/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AggrigationExamples;

/**
 *
 * @author SAGAR
 */
class Teacher{
    public int id;
    public String name;
    
    Teacher(int id,String name){
        this.id=id;
        this.name=name;
    }
    void display(){
        System.out.println("id ="+id+" "+"name ="+name);
    }
}
class Student{
    public int rollno;
    public String subject;
    Teacher t;
    
    Student(Teacher t,int rollno,String subject){
        this.t=t;
        this.rollno=rollno;
        this.subject=subject;
    }
    void display(){
        System.out.println(t.id);
        System.out.println(t.name);
        System.out.println(rollno);
        System.out.println(subject);
    }
}
public class AggrigationEx1 {
    public static void main(String[] args) {
        Teacher t1=new Teacher(111,"sagar");
        t1.display();
        System.out.println("-------------------------------------------------");
        Teacher t2=new Teacher(222,"ankita");
        t2.display();
        System.out.println("=================================================");
        Student s1=new Student(t1,1,"java");
        s1.display();
        System.out.println("-------------------------------------------------");
        Student s2=new Student(t2,2,"sql");
        s2.display();
    }
}