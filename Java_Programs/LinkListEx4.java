/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedList;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author SAGAR
 */
public class LinkListEx4 {
        public static void main(String args[]){  
                LinkedList<String> ll=new LinkedList<String>();  
                ll.add("Ravi");  
                ll.add("Vijay");  
                ll.add("Ajay");  
                //Traversing the list of elements in reverse order  
                Iterator i=ll.descendingIterator();  
                while(i.hasNext())  
                {  
                    System.out.println(i.next());  
                }  
        }
}