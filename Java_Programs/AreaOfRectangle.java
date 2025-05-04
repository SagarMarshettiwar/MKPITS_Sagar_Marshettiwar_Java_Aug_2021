/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MixedQuestions;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class AreaOfRectangle {
   public static void main(String args[]) 
    {   
      Scanner s= new Scanner(System.in);
      System.out.println("Enter the length:");
      double l= s.nextDouble();
      System.out.println("Enter the breadth:");
      double b= s.nextDouble();
      double  area=l*b;
      System.out.println("Area of Rectangle is: " + area);      
   }
}