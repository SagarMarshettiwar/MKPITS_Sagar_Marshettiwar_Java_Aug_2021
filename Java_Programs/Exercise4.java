/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionPackages;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Exercise4 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("Input the string: ");
        String str=s.nextLine();
        System.out.println("Count number :"+count_Vowel(str));
    }
    public static int count_Vowel(String s){
        int count=0;
        for(int i=0;i<=s.length()-1;i++){
            if (s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i'
                    || s.charAt(i) == 'o' || s.charAt(i) == 'u')
            {
                count++;
            }
        }
        return count;
    }
}    