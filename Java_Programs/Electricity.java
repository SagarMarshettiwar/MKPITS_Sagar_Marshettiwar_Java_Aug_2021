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
public class Electricity {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int customerid,unit;
	float amountcharge=0,surchargeamount=0,netamount;
	String name;
	System.out.println("enter customer id :");
	customerid=s.nextInt();
	System.out.println("enter customer name :");
	name=s.nextLine();
	System.out.println("enter unit consumed by customer :");
	unit=s.nextInt();
	if(unit<=199)
	{
		amountcharge=unit*1.20f;
	}
	else if(unit>=200 && unit<400)
	{
		amountcharge=unit*1.50f;
	}
	else if(unit>=400 && unit<600)
	{
		amountcharge=unit*1.80f;
	}
	else if(unit>=600 )
	{
		amountcharge=unit*2.00f;
	}
	else
	{
                System.out.println("invalid unit");
	}
	if(amountcharge <= 100)
	{
            amountcharge=100.0f;
	}
                System.out.println("amount charge ="+amountcharge);
	
	if(amountcharge >=400)
	{
	surchargeamount=(float)(15/100.0f)*amountcharge;
	       System.out.println("surcharge amount ="+surchargeamount);
        }else{
	netamount=amountcharge+surchargeamount;
	       System.out.println("net amount paid by the customer ="+netamount);
        }       
    }
}
