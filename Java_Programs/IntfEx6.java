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
interface Drawable{
    void draw();
}
class Rectangle implements Drawable{
    public void draw(){
        System.out.println("drawing rectangle");}
}
class Circle implements Drawable{
    public void draw(){
        System.out.println("drawing circle");
    }
}
public class IntfEx6{
    public static void main(String args[]){
        Drawable d=new Circle();//In real scenario, object is provided by method e.g. getDrawable()
        d.draw();
    }
}