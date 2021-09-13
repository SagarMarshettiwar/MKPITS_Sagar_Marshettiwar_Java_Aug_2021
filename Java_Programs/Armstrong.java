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
public class Armstrong {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter the number");
        int num=s.nextInt();
        int t=num;
        int sum=0;
        while(num>0){
            int r=num%10;
            sum=sum+(r*r*r);
            num=num/10;
        }
        if(t==sum){
            System.out.println("Armstrong");
        }else{
            System.out.println("not");
        }
    }
}
