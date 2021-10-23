/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;

/**
 *
 * @author SAGAR
 */
public class ArrListEx11 {
        public static void main(String [] args)  
        {  
                ArrayList<String> al=new ArrayList<String>();  
                System.out.println("Is ArrayList Empty: "+al.isEmpty());  
                al.add("Ravi");    
                al.add("Vijay");    
                al.add("Ajay");    
                System.out.println("After Insertion");  
                System.out.println("Is ArrayList Empty: "+al.isEmpty());   
       }
}