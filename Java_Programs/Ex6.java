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
public class Ex6 {
    public void isGreater(int num1,int num2,int num3){
        if(num1>num2 && num1>num3){
            System.out.println("num1 is greater");
        }else if(num2>num3 && num2>num3){
            System.out.println("num 2 is greater");
        }else{
            System.out.println("");
        }
    }   
    public static void main(String[] args) {
        int n1=5,n2=8;
        Ex5 e=new Ex5();
        e.isGreater(n1, n2);
    }
}
