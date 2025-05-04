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
public class LHSEx2 {
        public static void main(String args[]){  
                LinkedHashSet<String> al=new LinkedHashSet<String>();  
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
