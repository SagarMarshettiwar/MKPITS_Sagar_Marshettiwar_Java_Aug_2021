/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MixedQuestions;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Temperature {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter the number");
        int temp=s.nextInt();
        if(temp<0){
		System.out.println("freezing weather");
	}else if(temp>0 && temp<10){
		System.out.println("verycold weather");
	}else if(temp>10 && temp<20){
		System.out.println("cold weather");
	}else if(temp>20 && temp<30){
		System.out.println("normal weather");
	}else if(temp>30 && temp<40){
		System.out.println("hot weather");
	}else if(temp>=40){
		System.out.println("very hot weather");
	}
    }
}
