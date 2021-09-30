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
interface Shape{
    void draw();
} 
class circle implements Shape{
    public void draw(){
        System.out.println("code to draw circle");
    }
}
public class IntfEx1 {
    public static void main(String[] args) {
        Shape s=new circle();
        s.draw();
    }
}