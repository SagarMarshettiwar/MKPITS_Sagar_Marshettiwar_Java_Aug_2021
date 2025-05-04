/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ForLoopEx;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class SalaryFor {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int i=1;
        double TotalSal=0;
        while(i<=10){
            System.out.println("enter the Salary"+i);
            double Salary=s.nextDouble();
            TotalSal=TotalSal+Salary;
            i++;
        }
        System.out.println("Total Salary "+TotalSal);
        double avg=TotalSal/10.0;
        System.out.println("Average "+avg);
    }
}
