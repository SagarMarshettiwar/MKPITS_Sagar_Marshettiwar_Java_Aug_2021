/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class DoWhile5 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int sum=0,i=1;
        do{
            System.out.println("enter narks"+i);
            int marks=s.nextInt();
            sum=sum+marks;
            i++;
        }while(i<=5);
        System.out.println(sum);
        float per=(float) (sum/500.0f) * 100.0f;
        System.out.println(per);
    }
}
