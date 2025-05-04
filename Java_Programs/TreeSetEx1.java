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
public class TreeSetEx1 {
        public static void main(String args[]){  
                TreeSet<String> al=new TreeSet<String>();  
                al.add("Ravi");  
                al.add("Vijay");  
                al.add("Ravi");  
                al.add("Ajay");   
                Iterator<String> itr=al.iterator();  
                while(itr.hasNext()){  
                 System.out.println(itr.next());  
                }  
        }  
}
