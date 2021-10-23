/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Vegetable{
    int quantity;
    String name;

    public Vegetable(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }
}
public class LinkListEx7 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Vegetable arr[]=new Vegetable[3];
        List<Vegetable> veg=new ArrayList<Vegetable>();
        for(int i=0;i<arr.length;i++){
            System.out.println("Enter quantity");
            int quantity=sc.nextInt();
            System.out.println("Enter name");
            String name=sc.next();
            arr[i]=new Vegetable(quantity,name);
            veg.add(arr[i]);
        }
        for(Vegetable i:veg){
            System.out.println(i.name+" "+i.quantity);
        }
    }
}
