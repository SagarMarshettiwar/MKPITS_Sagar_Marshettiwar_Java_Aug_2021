/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeSet;

import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author SAGAR
 */
public class TreeSetEx2 {
        public static void main(String args[]){  
                TreeSet<String> set=new TreeSet<String>();  
                set.add("Ravi");  
                set.add("Vijay");  
                set.add("Ajay");  
                System.out.println("Traversing element through Iterator in descending order");  
                Iterator i=set.descendingIterator();  
                while(i.hasNext())  
                {  
                    System.out.println(i.next());  
                }  
        }  
}