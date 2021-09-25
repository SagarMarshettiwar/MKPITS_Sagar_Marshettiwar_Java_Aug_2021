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
class Person{
    void view(){
        System.out.println("super class");
    }
}
class Child extends Person{
    void show(){
        System.out.println("child class");
    }
}
class Teacher extends Child{
    void display(){
        System.out.println("Teacher class");
    }
}

public class InheritanceEx4 {
    public static void main(String[] args) {
        Teacher t=new Teacher();
        t.view();
        t.show();
        t.display();
    }
}
