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
public class SwitchDays {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int days;
	System.out.println("enter the number 1 to 7");
	days=s.nextInt();
	switch(days){
		case 1:
			System.out.println("\n mon");
			break;
		case 2:
			System.out.println("\n tue");
			break;
		case 3:
			System.out.println("\n wed");
			break;
		case 4:
			System.out.println("\n thus");
			break;
		case 5:
			System.out.println("\n fri");
			break;
		case 6:
			System.out.println("\n sat");
			break;
		case 7:
			System.out.println("\n sun");
			break;
		default :
			System.out.println("\n error");
			break;	
	}
    }
}
