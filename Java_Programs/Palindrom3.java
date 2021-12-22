/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mkpits;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Palindrom3 {
    public static void main(String args[])  
   {  
      String a; 
      String b = "";  
      Scanner in = new Scanner(System.in);   
      System.out.println("Enter a string");  
      a = in.nextLine();     
      for ( int i = a.length() - 1; i >= 0; i-- ){  
         b = b + a.charAt(i);  
      }
      if (a.equals(b)){  
         System.out.println("palindrome.");  
      }else{  
         System.out.println("not");   
      }  
    }
}    
