/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MixedQuestions;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class SumThreeNumbers {
    public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
          System.out.println("Enter numner 1 : ");
          int number1 = scanner.nextInt();

          System.out.println("Enter numner 2 : ");
          int number2 = scanner.nextInt();

          System.out.println("Enter numner 3 : ");
          int number3 = scanner.nextInt();

          int finalResult = number1 + number2 + number3;
          System.out.println("Final resut " + finalResult);

 }

}