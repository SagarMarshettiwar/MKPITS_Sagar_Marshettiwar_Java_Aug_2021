/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IFElse;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class SelectionConstruct6 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("ENTER THE AGE");
        int age=s.nextInt();
        if( age >= 18 ) {
            System.out.println(" you are elegible to vote");
        }else {
            System.out.println(" you are not elegible to vote");
        }
    }
}
