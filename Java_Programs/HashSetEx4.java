/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author SAGAR
 */
public class HashSetEx4 {
        public static void main(String args[]){  
                ArrayList<String> list=new ArrayList<String>();  
                list.add("Ravi");  
                list.add("Vijay");  
                list.add("Ajay");  
                HashSet<String> set=new HashSet(list);  
                set.add("Gaurav");  
                Iterator i=set.iterator();  
                while(i.hasNext())  
                {  
                System.out.println(i.next());  
                }  
        }  
}