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
public class WhileLoop8 {
        public static void main(String[] args) {
        int n1=0,n2=1,n3,i=2;
        Scanner s=new Scanner(System.in);
            System.out.println("enter the end number to find series");
            int num=s.nextInt();
            System.out.println(n1);
            System.out.println(n2);
            while(i<=num){
                n3=n1+n2;
                System.out.println(n3);
                n1=n2;
                n2=n3;
                i++;
            }
        }
}        
