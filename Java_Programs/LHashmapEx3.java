/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedhashMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author SAGAR
 */
public class LHashmapEx3 {
    public static void main(String args[]) {  
        Map<Integer,String> map=new LinkedHashMap<Integer,String>();        
        map.put(101,"Amit");    
        map.put(102,"Vijay");    
        map.put(103,"Rahul");    
        System.out.println("Before invoking remove() method: "+map);     
        map.remove(102);  
        System.out.println("After invoking remove() method: "+map);    
    }  
}
