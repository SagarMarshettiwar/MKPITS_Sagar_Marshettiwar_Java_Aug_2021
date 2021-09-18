/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticExample;

/**
 *
 * @author SAGAR
 */
class Student3{  
     int rollno;  
     String name;  
     static String college = "ITS";  
     //static method to change the value of static variable  
     static void change(){  
     college = "BBDIT";  
     }  
     //constructor to initialize the variable  
     Student3(int r, String n){  
     rollno = r;  
     name = n;  
     }  
     //method to display values  
     void display(){System.out.println(rollno+" "+name+" "+college);}  
}  
//Test class to create and display the values of object  
public class TestStaticMethod{  
    public static void main(String args[]){  
    Student3.change();//calling change method  
    //creating objects  
    Student3 s1 = new Student3(111,"Karan");  
    Student3 s2 = new Student3(222,"Aryan");  
    Student3 s3 = new Student3(333,"Sonoo");  
    //calling display method  
    s1.display();  
    s2.display();  
    s3.display();  
    }  
} 
