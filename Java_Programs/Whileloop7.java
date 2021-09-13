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
public class Whileloop7 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter the number");
        int num=s.nextInt();
        int i=1;
        while(i<=num){
            System.out.println(i);
            i++;
        }
    }
}
