/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Area1{
    int length,breadth;

    public Area1(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }
    int returnArea(){
        return length*breadth;
    }
}
public class TestEx7 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("Enter length");
        int l=s.nextInt();
        System.out.println("Enter breadth");
        int b=s.nextInt();
        Area1 a=new Area1(l,b);
        System.out.println("Area ="+a.returnArea());
    }
}
