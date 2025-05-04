/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InheritanceExamples;

/**
 *
 * @author SAGAR
 */
class Person1{
    void view(){
        System.out.println("super class");
    }
}
class Child1 extends Person1{
    void show(){
        System.out.println("child class");
    }
}
class Teacher1 extends Person{
    void display(){
        System.out.println("Teacher class");
    }
}

public class InheritanceEx5 {
    public static void main(String[] args) {
        Child1 c=new Child1();
        c.view();
        c.show();
        Teacher1 t=new Teacher1();
        t.view();
        t.display();
    }
}
