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
public class Ex7 {
    public boolean isGreater(int num1,int num2){
        if(num1>num2){
            return true;
        }else{
            return false;
        }
    }   
    public static void main(String[] args) {
        int n1=5,n2=8;
        Ex7 e=new Ex7(); 
        boolean s=e.isGreater(n1, n2);
        if(s==true){
            System.out.println("num1 is greater");
       }else{
            System.out.println("num2 is greater");
        }
    }
}
