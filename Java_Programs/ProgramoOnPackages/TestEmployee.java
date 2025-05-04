/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProgramoOnPackages;

import Hello.Employee;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class TestEmployee {
    public static void main(String[] arg)
{
    Employee r1=new Employee();
    Scanner scan=new Scanner(System.in);
    System.out.println("enter empno");
    int empno=scan.nextInt();
    System.out.println("enter empname");
    String empname=scan.next();
    System.out.println("enter basic salary");
    int basicsalary=scan.nextInt();
    r1.insert(empno,empname,basicsalary);
    r1.calculateBonus();
    r1.display();
    }
}
