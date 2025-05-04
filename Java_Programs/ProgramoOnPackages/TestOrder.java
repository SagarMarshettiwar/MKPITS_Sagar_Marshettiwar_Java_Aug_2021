/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProgramoOnPackages;
import Hello.Order;
import java.util.Scanner;
/**
 *
 * @author SAGAR
 */
public class TestOrder {
    public static void main(String[] args) {
        Order o=new Order();
        Scanner s=new Scanner(System.in);
        System.out.println("Enter Order no");
        int no=s.nextInt();
        System.out.println("Enter Order Date");
        String date=s.next();
        System.out.println("Enter Customer name");
        String name=s.next();
        System.out.println("Enter Product name");
        String pname=s.next();
        o.insert(no, date, name, pname);
        System.out.println("enter cost");
        int rate=s.nextInt();
        System.out.println("Enter Quantity");
        int quant=s.nextInt();
        System.out.println("-----------------------------------------------------------");
        int total=o.calculateBill(rate,quant);
        System.out.println("Total Bill ="+total);
        System.out.println("-------------------------------------------------------------");
        String show=o.Display();
        System.out.println("Show ="+show);
    }
}
