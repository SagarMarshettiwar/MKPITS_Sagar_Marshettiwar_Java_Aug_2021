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
public class FibboFor {
    public static void main(String[] args) {
        int n1=0,n2=1,n3;
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the number to find fibo series");
        int num=s.nextInt();
        System.out.println(n1);
        System.out.println(n2);
        for(int i=2;i<=num;i++){
            n3=n1+n2;
            System.out.println(n3);
            n1=n2;
            n2=n3;
        }
    }
}
