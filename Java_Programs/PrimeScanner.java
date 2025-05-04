/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimeNumber;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class PrimeScanner {
    public static void main(String[] args) {
        int t=0;
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the number");
        int num=s.nextInt();
            for(int j=2;j<num;j++){
                if(num%j==0){
                    t=t+1;
                }
            }    
            if(t==0){
                System.out.println("prime");
            }else{
                System.out.println("not prime");
            }
    }
}
