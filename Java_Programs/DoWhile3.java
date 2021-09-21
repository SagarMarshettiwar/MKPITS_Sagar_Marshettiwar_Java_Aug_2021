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
public class DoWhile3 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        char ch;
        do{
            System.out.println("Enter the number");
            int num=s.nextInt();
            int i=1;
            while(i<=10){
                int result=num*i;
                System.out.println(num+"*"+i+"="+result);
                i++;
            }
            System.out.println("continue press y");
            ch=s.next().charAt(0);
        }while(ch=='y');
    }
}
