/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeapYearQuestion;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class LeapYear {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter year to find");
        int year=s.nextInt();
        if(year%4==0){
            if(year%100==0){
                if(year%400==0){
                    System.out.println("leap year");
                }else{
                    System.out.println("not leap year");
                }
                
            }else{
                System.out.println("leap year");
            }
            
        }else{
            System.out.println("not leap year");
        }
    }
}
