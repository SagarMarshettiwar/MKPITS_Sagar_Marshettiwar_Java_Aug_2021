/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWitch;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Vowel {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter character");
        char c=s.next().charAt(0);
        switch(c){
            case 'a':
            case 'e':
            case 'i':
            case 'o':        
            case 'u':
                System.out.println("vowerl");
                break;
            default:
                System.out.println("not vowel");
                break;
        }
    }
}
