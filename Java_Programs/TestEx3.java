/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

/**Assign and print the roll_number, phone_number and address of two students having names "Sam" and
"John" respectively by creating two objects of class 'Student'.
 *
 * @author SAGAR
 */
class Student1{
    String name;
    int rollno;
    long phoneno;
    String Address;

    @Override
    public String toString() {
        return "Student1{" + "name=" + name + ", rollno=" + rollno + ", phoneno=" + phoneno + ", Address=" + Address + '}';
    }
    
}
public class TestEx3 {
    public static void main(String[] args) {
        Student1 s1=new Student1();
        s1.name="john";
        s1.rollno=1;
        s1.phoneno=1234567890;
        s1.Address="aassdd";
        System.out.println(s1.toString());
        Student1 s2=new Student1();
        s2.name="Sam";
        s2.rollno=2;
        s2.phoneno=85412546;
        s2.Address="fghjk";
        System.out.println(s2.toString());
    }
}
