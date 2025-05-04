/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IFElse;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class SelectionConstruct11 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("Enter 2 number");
        int num1=s.nextInt();
        int num2=s.nextInt();
        System.out.println("Enter operator (+,-,*,/)");
        char op=s.next().charAt(0);
        int result=0;
        if(op == '+'){
		result=num1+num2;
	}else if(op == '-'){
		result=num1-num2;
	}else if(op == '*'){
		result=num1*num2;
	}else if(op == '/'){
		result=num1/num2;
	}else{
		System.out.println("invalid operator");
	}
		System.out.println("the result is = "+result);
    }
}
