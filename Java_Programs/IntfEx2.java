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
interface Shape1 {
    void draw();
}
class circle1 implements Shape1 {
    public void draw( ) {
        System.out.println("code to draw circle");
    }
}
class rectangle1 implements Shape1 {
    public void draw( ) {
        System.out.println("code to draw rectangle");
    }
}
public class IntfEx2 {
    public static void main(String[] arg) {
        Shape1 s=new circle1();
        s.draw();
        s=new rectangle1();
        s.draw();
    }
}