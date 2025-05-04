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
public class HashSetEx2 {
        public static void main(String[] args) {
            HashSet<String> set=new HashSet<String>();  
                set.add("Ravi");  
                set.add("Vijay");  
                set.add("Ravi");  
                set.add("Ajay"); 
                Iterator itr=set.iterator();  
                while(itr.hasNext()){  
                System.out.println(itr.next());  
                }  
        }  
}