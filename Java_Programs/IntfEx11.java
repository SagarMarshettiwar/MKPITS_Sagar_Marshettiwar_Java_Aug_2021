/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceExamples;

/**
 *
 * @author SAGAR
 */
interface Drawable2{
    void draw();
    default void msg(){
        System.out.println("default method");
    }
}
class Rectangle2 implements Drawable2{
    public void draw(){
        System.out.println("drawing rectangle");
    }
}
public class IntfEx11{
    public static void main(String args[]){
        Drawable2 d=new Rectangle2();
        d.draw();
        d.msg();
    }
}