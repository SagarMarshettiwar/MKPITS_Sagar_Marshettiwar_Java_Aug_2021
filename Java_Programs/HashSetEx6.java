/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HashSet;

import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Book1{
    int id;
    String name,author;
    int quantity;

    public Book1(int id, String name, String author, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
    }
}
public class HashSetEx6 {
        public static void main(String[] args) {           
            Scanner sc=new Scanner(System.in);
            Book1 b[]=new Book1[3];
            HashSet<Book1> hs=new HashSet<Book1>();
            for(int i=0;i<3;i++){
                System.out.println("Enter id");
                int id=sc.nextInt();
                System.out.println("emter name");
                String name=sc.next();
                System.out.println("enter author");
                String author=sc.next();
                System.out.println("enter quantity");
                int quantity=sc.nextInt();
                b[i]=new Book1(id,name,author,quantity);
                hs.add(b[i]);
            }
            for(Book1 j:hs){
                System.out.println(j.id+" "+" "+j.name+" "+j.author+" "+j.quantity);
            }
        }
}
