/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperKeyWordExamples;

/**
 *
 * @author SAGAR
 */
class Manager{
    void display(){
            System.out.println("hello Boss");
    }
}
class Employee extends Manager{
    void display(){
            System.out.println("hello Emp");
            super.display();
    }
}
public class SuperEx2 {
    public static void main(String[] args) {
        Employee e=new Employee();
        e.display();
    }
}
