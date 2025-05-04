/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeSet;

import java.util.TreeSet;

/**
 *
 * @author SAGAR
 */
public class TreeSetEx4 {
        public static void main(String[] args) {
                TreeSet<String> set=new TreeSet<String>();  
                 set.add("A");  
                 set.add("B");  
                 set.add("C");  
                 set.add("D");  
                 set.add("E");  
                 System.out.println("Initial Set: "+set);  
                 System.out.println("Reverse Set: "+set.descendingSet());  
                 System.out.println("Head Set: "+set.headSet("C", true));  
                 System.out.println("SubSet: "+set.subSet("A", false, "E", true));  
                 System.out.println("TailSet: "+set.tailSet("C", false));  
        }  
}