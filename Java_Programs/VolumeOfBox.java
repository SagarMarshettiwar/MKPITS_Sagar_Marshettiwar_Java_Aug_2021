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
public class VolumeOfBox 
{
      
   public static void main(String args[]) 
    {   
       
        	Scanner s= new Scanner(System.in);
         	System.out.println("Enter the length of box:");
         	double l=s.nextDouble();
		System.out.println("Enter the breadth of box:");
         	double b=s.nextDouble();
		System.out.println("Enter height of box:");
         	double h=s.nextDouble();
                
                 double  volume= l*b*h;
 
             System.out.println("Volume Of boxis:" +volume);
             
          
   }
}