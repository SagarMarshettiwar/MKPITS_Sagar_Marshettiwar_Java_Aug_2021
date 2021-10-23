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
public class LinkListEx1 {
    public static void main(String args[]){  
        LinkedList<String> al=new LinkedList<String>();  
        al.add("Ravi");  
        al.add("Vijay");  
        al.add("Ravi");  
        al.add("Ajay");  
        Iterator itr=al.iterator();  
        while(itr.hasNext()){  
            System.out.println(itr.next());  
        }  
    }  
}