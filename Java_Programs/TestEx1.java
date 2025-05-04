/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

/**
 *Write a program to print the area of a rectangle by creating a class named 'Area' having two methods.
    First method named as 'setDim' takes length and breadth of rectangle as parameters and the second
    method named as 'getArea' returns the area of the rectangle. Length and breadth of rectangle are
    entered through keyboard.
 *
 */
class Area{
    public int length,breadth;
    void setDim(int length,int breadth){
        this.length=length;
        this.breadth=breadth;
    }
    int getArea(){
        return length*breadth;
    }
}
public class TestEx1 {
    public static void main(String[] args) {
        Area a=new Area();
        a.setDim(5, 5);
        System.out.println("Area of rectangle is ="+a.getArea());
    }
}