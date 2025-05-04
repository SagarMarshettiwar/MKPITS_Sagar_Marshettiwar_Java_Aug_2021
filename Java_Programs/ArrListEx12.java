/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
public class ArrListEx12 {
        public static void main(String[] args) {           
            Book b1=new Book(1,"java","oracle",4);
            Book b2=new Book(2,"sql","abc",3);
            Book b3=new Book(3,".net","xyz",2);
            List<Book> lst=new ArrayList<Book>();
            lst.add(b1);
            lst.add(b2);
            lst.add(b3);
//            for(Book b:lst){
//                System.out.println(b.id+" "+b.name+" "+b.author+" "+b.quantity);
//            }
//            Iterator itr=lst.iterator();
//            while(itr.hasNext()){
//                Book b=(Book)itr.next();
//                System.out.println(b.id+" "+b.name+" "+b.author+" "+b.quantity);
//            }
            Iterator<Book>itr=lst.iterator();
            while(itr.hasNext()){
                Book b=itr.next();
                System.out.println(b.id+" "+b.name+" "+b.author+" "+b.quantity);
            }
    }
}
