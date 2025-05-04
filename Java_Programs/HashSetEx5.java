/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HashSet;

import java.util.HashSet;
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
public class HashSetEx5 {
        public static void main(String[] args) {           
            Book b1=new Book(1,"java","oracle",4);
            Book b2=new Book(2,"sql","abc",3);
            Book b3=new Book(3,".net","xyz",2);
            HashSet<Book> hst=new HashSet<Book>();
            hst.add(b1);
            hst.add(b2);
            hst.add(b3);
            for(Book b:hst){
                System.out.println(b.id+" "+b.name+" "+b.author+" "+b.quantity);
            }
    }
}
