/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Emp{
    public  int salary,hour;
    void getInfo(int salary,int hour){
        this.salary=salary;
        this.hour=hour;
    }
    int addSal(int adds){
        int salary0;
        if(salary<500){
            salary0=salary+adds;
        }else{
            salary0=salary;
        }
        return salary0;
    }
    int addWork(int hr){
        int salary1;
        if(hour>6){
            salary1=salary+hr;
        }else{
            salary1=salary;
        }
        return salary1;
    }
}
public class TestEx11 {
    public static void main(String[] args) {
        Emp e=new Emp();
        Scanner s=new Scanner(System.in);
        System.out.println("Enter Salary");
        int sal=s.nextInt();
        System.out.println("Enter Hour");
        int hou=s.nextInt();
        e.getInfo(sal, hou);
        System.out.println("Add Salary ="+e.addSal(10));
        System.out.println("Add Work ="+e.addWork(5));
    }
}
