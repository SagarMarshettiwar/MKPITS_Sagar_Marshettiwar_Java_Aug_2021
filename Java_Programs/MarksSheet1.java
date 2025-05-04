/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package While;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class MarksSheet1 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int maths,physics,chemistry,total1=0,total2=0;
	System.out.println("enter maths marks\n ");
	maths=s.nextInt();
	System.out.println("enter physics marks\n");
	physics=s.nextInt();
	System.out.println("enter chemistry marks\n");
	chemistry=s.nextInt();
	total1=maths+physics+chemistry;
	System.out.println("the total of PCM ="+total1);
	total2=maths+physics;
	System.out.println("the total of PM ="+total2);
	if(total1>=190 || total2>=140){
		System.out.println("eligible to admission");
	}else{
		System.out.println("not-eligible to admission");
	}
    }    
}
