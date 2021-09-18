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
class Student1{
    int rollno;//instance variable
    String name;
    static String college ="ITS";//static variable
//constructor
Student1(int r, String n){
    rollno = r;
    name = n;

}
//method to display the values
void display (){
    System.out.println(rollno+" "+name+" "+college);}
}
//Test class to show the values of objects
public class TestStaticVariable2{
    public static void main(String args[]){
        Student1 s1 = new Student1(111,"Karan");
        Student1 s2 = new Student1(222,"Aryan");
        //we can change the college of all objects by the single line of code
        Student.college="BBDIT";
        s1.display();
        s2.display();
    }
}
