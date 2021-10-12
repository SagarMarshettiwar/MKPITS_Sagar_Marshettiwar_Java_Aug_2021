/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples6;

import java.io.Console;
/**
 *
 * @author SAGAR
 */
public class ConsoleEx1 {
    public static void main(String[] args) {
        Console c=System.console();
        System.out.println("Enter name");
        String n=c.readLine();
        System.out.println("Welcome "+n);
    }
}