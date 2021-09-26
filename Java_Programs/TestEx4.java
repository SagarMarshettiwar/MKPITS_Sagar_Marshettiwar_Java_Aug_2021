package TestExamples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**Write a program to print the area and perimeter(Perimeter = sum of the three sides) of a triangle having
sides of 3, 4 and 5 units by creating a class named 'Triangle' without any parameter in its constructor.
 *
 * @author SAGAR
 */
class Triangle{
    int side1,side2,side3,base,height;
    public Triangle() {
        this.side1=3;
        this.side2=4;
        this.side3=5;
    }
    int Parameter(){
       return side1*side2*side3; 
    }

    public Triangle(int base, int height) {
        this.base = base;
        this.height = height;
    }
    int area(){
        return (base*height)/2;
    }
}
public class TestEx4 {
    public static void main(String[] args){ 
        Triangle t1=new Triangle(5,5);
        Triangle t2=new Triangle();
        System.out.println("parameter ="+t2.Parameter());
        System.out.println("Area ="+t1.area());
    }
}
