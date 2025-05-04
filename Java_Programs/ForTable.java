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
public class ForTable {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the number");
        int num=s.nextInt();
        for(int i=1;i<=10;i++){
            int result=num*i;
            System.out.println(num+"*"+i+"="+result);
        }
    }
}
