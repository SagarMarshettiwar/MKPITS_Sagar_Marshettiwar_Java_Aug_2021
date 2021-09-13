/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package While;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Whileloop9 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the no to check prime");
        int num=s.nextInt();
        int i=2,t=0;
        while(i<num){
            if(num%i==0){
                t=t+1;
            }
            i++;
        }
        if(t==0){
            System.out.println("prime");
        }else{
            System.out.println("not prime");
        }
    }
}
