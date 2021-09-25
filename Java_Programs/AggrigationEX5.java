/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperKeyWordExamples;

/**
 *
 * @author SAGAR
 */
class Operation{
    int square(int r){
        return r*r;
    }
}
class Circle{
    Operation op;
    double pi=3.14;
    double area(int radius){
         return pi*op.square(radius);
    }
}
public class AggrigationEX5 {
    public static void main(String[] args) {
        Circle c=new Circle();
        System.out.println("ans ="+c.area(10));
    }
}
