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
public class Whileloop5 {
    public static void main(String[] args) {
        int fact=1;
        Scanner s=new Scanner(System.in);
        System.out.println("enter the number");
        int num=s.nextInt();
        while(num>0){
            fact=fact*num;
            num--;
        }
        System.out.println(fact);
    }
}
