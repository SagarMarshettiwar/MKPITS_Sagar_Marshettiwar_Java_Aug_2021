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
interface Drawable4{
    void draw();
    interface drawcube {
        int cube(int x);
    }
}
class Rectangle4 implements Drawable4.drawcube{
    public void draw(){
        System.out.println("drawing rectangle");
    }
    public int cube(int x){
        return x*x*x;
    }
}
public class IntfEx14{
    public static void main(String args[]){
        Rectangle4 d=new Rectangle4();
        d.draw();
        System.out.println(d.cube(3));
    }
}