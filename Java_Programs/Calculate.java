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
public class Calculate {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter two numbers");
        int num1=s.nextInt();
        int num2=s.nextInt();
        System.out.println("enter the operator (+,-,*,/)");
        int op=s.next().charAt(0);
        int result=0;
        switch(op){
		case '+':
			result=num1+num2;
			break;
		case '-':
			result=num1-num2;
			break;
		case '*':
			result=num1*num2;
			break;
		case '/':
			result=num1/num2;
			break;
		default :
			
                    System.out.println("invalid operator");
	}
                    System.out.println("result ="+result);
    }
}
