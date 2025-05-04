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
public class SelectionConstruct8 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter Salary");
        int salary=s.nextInt();
        if(salary>10000){
		System.out.println("you get the bonus of 1000 rs");
	}else if(salary>5000 && salary<10000){
		System.out.println("you get the bonus of 500 rs");
	}else if(salary<5000){
		System.out.println("you get the bonus of 100 rs");
	}
    }
}    
