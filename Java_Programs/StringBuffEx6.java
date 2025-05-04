/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringBuffer;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class StringBuffEx6 {
    public static void main(String[] args) {
        String s1="";
        String s2="";
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the String");
        String str=sc.nextLine();
        StringBuffer sb=new StringBuffer(str);
        s1=sb.toString();
        sb.reverse();
        s2=sb.toString();
        if(s1.equals(s2)){
            System.out.println("String is palendrome");
        }else{
            System.out.println("not palindrome");
        }
    }
}