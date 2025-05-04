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
public class Whileloop6 {
    public static void main(String[] args) {
        int result,num;
        int i=1;
        Scanner s=new Scanner (System.in);
        System.out.println("enter the number");
        num=s.nextInt();
        while(i<=10){
            result=num*i;
            System.out.println(num+"*"+i+"="+result);
            i++;
        }
    }
}
