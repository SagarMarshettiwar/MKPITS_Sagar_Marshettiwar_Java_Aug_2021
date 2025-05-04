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
public class CMEx4 {
    public static void main(String[] args) {  
        try{    
            String s=null;
            System.out.println(s.length());
        }    
        catch(ArithmeticException e)  
        {  
            System.out.println("Arithmetic Exception occurs");  
        }    
        catch(ArrayIndexOutOfBoundsException e)  
        {  
           System.out.println("ArrayIndexOutOfBounds Exception occurs");  
        }    
        catch(Exception e)  
        {  
           System.out.println("Parent Exception occurs");  
        }             
           System.out.println("rest of the code");    
    }
}
