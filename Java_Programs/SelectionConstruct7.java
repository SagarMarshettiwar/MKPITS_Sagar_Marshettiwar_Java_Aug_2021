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
public class SelectionConstruct7 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter 3 nos");
        int num1=s.nextInt();
        int num2=s.nextInt();
        int num3=s.nextInt();
        if(num1<num2 && num1<num3){
		System.out.println("num1 is smallest");
	}else if(num2<num3){
		System.out.println("num2 is smallest");
	}else{
		System.out.println("num3 is smallest");
	}
    }
}
