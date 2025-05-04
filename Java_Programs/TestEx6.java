/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

/**
 *
 * @author SAGAR
 */
class Rectangle{
    int length,breadth;

    public Rectangle(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }
    int Area(){
        return length*breadth;
    }
}
public class TestEx6 {
    public static void main(String[] args) {
        Rectangle r=new Rectangle(4,5);
        Rectangle r1=new Rectangle(5,8);
        r.Area();
        r1.Area();
    }
}
