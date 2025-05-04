/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HashSet;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author SAGAR
 */
public class HashSetEx1 {
    public static void main(String[] args) {
        HashSet<String> set=new HashSet();  
            set.add("One");    
            set.add("Two");    
            set.add("Three");   
            set.add("Four");  
            set.add("Five");  
            Iterator i=set.iterator();  
            while(i.hasNext())  
            {  
            System.out.println(i.next());  
            }  
    }  
}