/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionPackages;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Exercise3 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("input a String");
        String str=s.nextLine();
        System.out.print("The middle character in the string: " + middle(str)+"\n");
    }
 public static String middle(String str)
    {
        int position;
        int length;
        if (str.length() % 2 == 0)
        {
            position = str.length() / 2 - 1;
            length = 2;
        }
        else
        {
            position = str.length() / 2;
            length = 1;
        }
        return str.substring(position, position + length);
    }
}