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
abstract class Shape{  
    abstract void draw();
    abstract void run();
}    
class Rectangle extends Shape{  
    void draw(){
        System.out.println("drawing rectangle");
    }
    void run(){
        System.out.println("Running");
    }
}  
class Circle extends Rectangle{  
    void draw1(){
        System.out.println("drawing circle");
        super.draw();
        super.run();
    }  
}    
public class AbstractEx4{  
    public static void main(String args[]){  
        Shape s=new Circle();
        Shape r=new Rectangle();
        r.draw();
        r.run();
        s.draw();//drawing rectangle
        Circle c=new Circle();
        c.run();
        c.draw1();
        Rectangle r1=new Rectangle();
        r1.run();
        r1.draw();
    }  
}