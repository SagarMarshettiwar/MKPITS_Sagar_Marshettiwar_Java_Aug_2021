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
public class MarkSFor {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter your rollno");
        int rollno=s.nextInt();
        System.out.println("enter your name");
        String name=s.nextLine();
        int total=0;
        for(int i=1;i<=5;i++){
            System.out.println("enter the marks"+i);
            int marks=s.nextInt();
            total=total+marks;
        }
        System.out.println("Roll number "+rollno);
        System.out.println("Name "+name);
        System.out.println("Total Marks "+total);
        double per=(double)(total/500.0f)*100.0;
        System.out.println("percentage"+per);
        if(per>=75){
            System.out.println("Distinction");
        }else if(per>=60 && per<75){
            System.out.println("First class");
        }else{
            System.out.println("fail");
        }    
    }
}