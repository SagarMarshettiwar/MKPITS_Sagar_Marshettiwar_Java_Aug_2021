/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HashMap;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SAGAR
 */
public class HashmapEx2 {
    public static void main(String args[]){  
        HashMap<Integer,String> map=new HashMap<Integer,String>();//Creating HashMap    
        map.put(1,"Mango");    
        map.put(2,"Apple");    
        map.put(3,"Banana");   
        map.put(1,"Grapes");   

        System.out.println("Iterating Hashmap...");  
        for(Map.Entry m : map.entrySet()){    
         System.out.println(m.getKey()+" "+m.getValue());    
        }  
     }  
}
