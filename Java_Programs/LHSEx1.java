/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedHashSet;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * @author SAGAR
 */
public class LHSEx1 {
        public static void main(String args[]){  
        LinkedHashSet<String> set=new LinkedHashSet();  
               set.add("One");    
               set.add("Two");    
               set.add("Three");   
               set.add("Four");  
               set.add("Five");  
               Iterator<String> i=set.iterator();  
               while(i.hasNext())  
               {  
               System.out.println(i.next());  
               }  
        }  
}
