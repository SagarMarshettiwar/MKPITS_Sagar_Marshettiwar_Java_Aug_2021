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
public class Vowel1 {
    public static void main(String[] args){
	char choice;
	System.out.println("enter any alphabet (a-z)");
	Scanner s=new Scanner(System.in);
        choice=s.next().charAt(0);
	if(choice=='a')
	{
	    System.out.println("char is vowel");
	}
	else if(choice=='e')
	{
            System.out.println("char is vowel");
	}
	else if(choice=='i')
	{
            System.out.println("char is vowel");
	}	
	else if(choice=='o')
	{
            System.out.println("char is vowel");
	}
	else if(choice=='u')
	{
            System.out.println("char is vowel");
	}
	else
	{
            System.out.println("char is not vowel");
	}
}
}
