/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

/**
 *
 * @author SAGAR
 */
class Employee{
    String name;
    int year;
    String address;

    public Employee(String name, int year, String address) {
        this.name = name;
        this.year = year;
        this.address = address;
    }
}
public class TestEx10 {
    public static void main(String[] args) {
        Employee e1=new Employee("Robert",1994,"64C-WallsStreat");
        Employee e2=new Employee("Sam",2000,"68D-WallsStreat");
        Employee e3=new Employee("John",1999,"26B-WallsStreat");
        
        System.out.println("Name"+"\t"+"Year of joining"+"  \t"+"Address");
        System.out.println(e1.name+"\t    "+e1.year+"         \t"+e1.address);
        System.out.println(e2.name+"\t    "+e2.year+"         \t"+e2.address);
        System.out.println(e3.name+"\t    "+e3.year+"         \t"+e3.address);
    }
}
