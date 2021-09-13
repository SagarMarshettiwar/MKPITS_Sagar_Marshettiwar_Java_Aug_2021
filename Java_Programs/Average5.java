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
public class Average5 {

    public static void main(String args[]) {

        int num = 5;
        double total = 0;
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < num; i++) {
            System.out.print("Please enter a number: ");
            total += sc.nextInt();
        }

        System.out.println("Average : " + (total / num));

    }
}
