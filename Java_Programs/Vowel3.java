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
public class Vowel3 {
    public static void main(String[] args) {
        char choice;
        Scanner s=new Scanner(System.in);
        System.out.println("enter the character");
        choice=s.next().charAt(0);
        if(choice=='a' || choice=='e' || choice=='i' || choice=='o' || choice=='u')
	{
	System.out.println("char is vowel");
	}
	else
	{
	System.out.println("char is not vowel");
	}
    }
}
