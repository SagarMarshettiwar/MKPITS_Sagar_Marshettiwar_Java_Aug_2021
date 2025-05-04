/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionPackages;

/**
 *
 * @author SAGAR
 */
class TestPerfect1  
{  
    public static boolean isPerfectNumber(int num)  
    {    
        int sum = 1;    
        for (int i = 2; i <= num/2; i++)  
        {  
            if (num % i==0)  
            {       
                sum = sum + i;  
            }
        }  
        if (sum == num){    
            return true;
        }else{  
            return false;  
        }
    }
}
public class Ex4{
    public static void main (String args[])  
    {  
        System.out.println("Perfect Numbers between 2 to 10000 are: ");    
        for (int n = 2; n < 10000; n++){  
            if (TestPerfect1.isPerfectNumber(n)){
                System.out.println(n +" is a perfect number");
            }  
        }  
    }
}    