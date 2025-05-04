/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class DoWhile2 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        char ch;
        int fact=1;
        do{
            System.out.println("Enter the no");
            int num=s.nextInt();
            while(num>0){
                fact=fact*num;
                num--;
            }
            System.out.println(fact);
            fact=1;
            System.out.println("do you want to continue");
            ch=s.next().charAt(0);
        }while(ch=='y');
    }
}
