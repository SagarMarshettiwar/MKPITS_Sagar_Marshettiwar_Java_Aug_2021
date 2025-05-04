/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThisKeywordExample;

/**
 *
 * @author SAGAR
 */
class Student{
    int rollno;
    String name;
    float fee;

    public Student(int rollno, String name, float fee) {
        this.rollno = rollno;
        this.name = name;
        this.fee = fee;
    }
    void display(){
        System.out.println(rollno+" "+name+" "+fee);
    }
}

public class TestThis {
    public static void main(String[] args) {
        Student s1=new Student(111,"ankit",5000f);  
        Student s2=new Student(112,"sumit",6000f);  
        s1.display();  
        s2.display(); 
    }
}
