/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CatchMultiple;

/**
 *
 * @author SAGAR
 */
public class CMEx5 {
    public static void main(String[] args) {  
        try{    
            int a[]=new int[5];    
            a[5]=30/0;    
        }    
        catch(ArithmeticException e)  
        {  
            System.out.println("Arithmetic Exception occurs");  
            int a1[]=new int[5];
            try{
                a1[5]=30;
            }
            catch(ArrayIndexOutOfBoundsException e1)  
            {  
                System.out.println("ArrayIndexOutOfBounds Exception occurs");  
            }                
           System.out.println("rest of the code");    
        }
    }
}    
