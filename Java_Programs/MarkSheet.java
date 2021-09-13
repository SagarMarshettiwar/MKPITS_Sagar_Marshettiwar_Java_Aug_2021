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
public class MarkSheet {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter your rollno");
        int rollno=s.nextInt();
        System.out.println("enter your name");
        String name=s.nextLine();
        int i=1;
        int total=0;
        while(i<=3){
            System.out.println("enter the marks"+i);
            int marks=s.nextInt();
            total=total+marks;
            i++;
        }
        System.out.println("Roll number "+rollno);
        System.out.println("Name "+name);
        System.out.println("Total Marks "+total);
        double per=(double)(total/300.0f)*100.0;
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
