/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedList;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author SAGAR
 */
class Book{
    int id;
    String name,author;
    int quantity;

    public Book(int id, String name, String author, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
    }
}
public class LinkListEx5 {
        public static void main(String[] args) {           
            Book b1=new Book(1,"java","oracle",4);
            Book b2=new Book(2,"sql","abc",3);
            Book b3=new Book(3,".net","xyz",2);
            List<Book> lst=new LinkedList<Book>();
            lst.add(b1);
            lst.add(b2);
            lst.add(b3);
            for(Book b:lst){
                System.out.println(b.id+" "+b.name+" "+b.author+" "+b.quantity);
            }
    }
}
