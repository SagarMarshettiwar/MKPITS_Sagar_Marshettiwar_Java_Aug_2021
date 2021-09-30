/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractionExamples;

/**
 *
 * @author SAGAR
 */
abstract class Shape1{  
    abstract void draw();  
}    
class Rectangle1 extends Shape1{  
    void draw(){
        System.out.println("drawing rectangle");
    }
    void run(){
        System.out.println("Running");
    }
}  
class Circle2 extends Shape1{  
    void draw(){
        System.out.println("drawing circle");
    }  
}    
public class AbstractEx5{  
    public static void main(String args[]){  
        Shape1 s=new Circle2();
        s.draw();
        Shape1 r=new Rectangle1();
        r.draw();
    }  
}
