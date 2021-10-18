/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringExamples;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class StringEx5 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Email");
        String e=sc.nextLine();
        boolean s=e.contains(".com");
        if(s==true){
            System.out.println("valid");
        }else{
            System.out.println("invalid");
        }
    }
}
