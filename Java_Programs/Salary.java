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
public class Salary {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int basic_sal,hra,da,totalsalary;
	System.out.println("enter the basic salary ");
        basic_sal=s.nextInt();
	hra=25 % basic_sal;
	da=35 % basic_sal;
	totalsalary=hra+da+basic_sal;
	System.out.println("the tsalary is "+totalsalary);
    }
}
