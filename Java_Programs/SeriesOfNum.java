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
public class SeriesOfNum {
    public static void main(String[] args) {
        int i=1,num;
        System.out.println("enter the number");
        Scanner s=new Scanner(System.in);
        num=s.nextInt();
        while(i<=num){
            System.out.println(i);
            i++;
        }
    }
}
