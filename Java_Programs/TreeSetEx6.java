/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeSet;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author SAGAR
 */
class Book implements Comparable<Book>{
    int id;
    String name,author;
    int quantity;

    public Book(int id, String name, String author, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
    }
    public int compareTo(Book b) {  
        if(id>b.id){  
            return 1;  
        }else if(id<b.id){  
            return -1;  
        }else{  
        return 0;  
        }  
    }  
}
public class TreeSetEx6 {
        public static void main(String[] args) {           
            Book b1=new Book(1,"java","oracle",9);
            Book b2=new Book(2,"sql","abc",8);
            Book b3=new Book(3,".net","xyz",7);
            Set<Book> set=new TreeSet<Book>();
            set.add(b1);
            set.add(b2);
            set.add(b3);
            for(Book b:set){
                System.out.println(b.id+" "+b.name+" "+b.author+" "+b.quantity);
            }
    }
}

