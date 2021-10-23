/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedList;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Product{
    int id;
    String name;
    int quantity;

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}
public class LinkListEx6 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Product arr[]=new Product[3];
        List <Product> lst=new LinkedList<Product>();
        for(int i=0;i<3;i++){
            System.out.println("enter id");
            int id=sc.nextInt();
            System.out.println("enter name");
            String name=sc.next();
            System.out.println("Enter Quantity");
            int quantity=sc.nextInt();
            arr[i]=new Product(id,name,quantity);
            lst.add(arr[i]);
        }
        for(Product p:lst){
            System.out.println(p.id+" "+p.name+" "+p.quantity);
        }
    }
}    