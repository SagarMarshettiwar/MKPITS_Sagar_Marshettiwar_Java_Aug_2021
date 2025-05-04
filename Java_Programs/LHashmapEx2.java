/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedhashMap;

import java.util.LinkedHashMap;

/**
 *
 * @author SAGAR
 */
public class LHashmapEx2 {
    public static void main(String args[]){  
        LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();           
            map.put(100,"Amit");    
            map.put(101,"Vijay");    
            map.put(102,"Rahul");    
            System.out.println("Keys: "+map.keySet());  
            System.out.println("Values: "+map.values());    
            System.out.println("Key-Value pairs: "+map.entrySet());  
      }  
}
