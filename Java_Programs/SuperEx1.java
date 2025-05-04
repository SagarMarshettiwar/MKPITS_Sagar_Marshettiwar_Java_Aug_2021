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
class parents{
    String name="sagar";
}
class Child extends parents{
    String name="ankita";
    void display(){
        System.out.println("child name ="+name);
        System.out.println("pdrent name ="+super.name);
    }
}
public class SuperEx1 {
    public static void main(String[] args) {
        Child c=new Child();
        c.display();
    }
}
