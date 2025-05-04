/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

/**Write a program to print the area and perimeter of a triangle having sides of 3, 4 and 5 units by creating
a class named 'Triangle' with constructor having the three sides as its parameters.
 *
 * @author SAGAR
 */
class Triangle1{
    int side1,side2,side3,base,height;
    public Triangle1(int side1,int side2,int side3) {
        this.side1=side1;
        this.side2=side2;
        this.side3=side3;
    }
    int Parameter1(){
       return side1*side2*side3; 
    }

    public Triangle1(int base, int height) {
        this.base = base;
        this.height = height;
    }
    double area1(){
        return (base*height)/2;
    }
}
public class TestEx5 {
    public static void main(String[] args){ 
        Triangle1 t1=new Triangle1(5,5);
        Triangle1 t2=new Triangle1(3,4,5);
        System.out.println("parameter ="+t2.Parameter1());
        System.out.println("Area ="+t1.area1());
    }
}
