/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;

/**
 *
 * @author SAGAR
 */
public class ArrListEx8 {
        public static void main(String args[]){  
                ArrayList<String> al=new ArrayList<String>();  
                System.out.println("Initial list of elements: "+al);  
                //Adding elements to the end of the list  
                al.add("sagar");  
                al.add("Ankita");  
                al.add("Dolly");  
                System.out.println("Array List"+al);  
                //Adding an element at the specific position  
                al.add(1, "Ameya");  
                System.out.println("After invoking add(int index, E element) method: "+al);  
                ArrayList<String> al2=new ArrayList<String>();  
                al2.add("Juhi");  
                al2.add("Hanuman");  
                //Adding second list elements to the first list  
                al.addAll(al2);
                System.out.println("After invoking addAll(Collection<? extends E> c) method: "+al);  
                ArrayList<String> al3=new ArrayList<String>();  
                al3.add("John");  
                al3.add("Rahul");  
                //Adding second list elements to the first list at specific position  
                al.addAll(1, al3);  
                System.out.println("After invoking addAll(int index, Collection<? extends E> c) method: "+al);  
        }
}
