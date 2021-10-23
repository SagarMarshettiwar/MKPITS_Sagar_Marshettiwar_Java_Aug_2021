/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapInterface;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SAGAR
 */
public class MapInterfaceEx3 {
    public static void main(String[] args) {
        Map<Integer,String> map=new HashMap<Integer,String>();
        map.put(100,"Amit");  
        map.put(101,"Vijay");  
        map.put(102,"Rahul");
        map.put(101, "sagar");
        for(Map.Entry m:map.entrySet()){
            System.out.println(m.getKey()+" "+m.getValue());
        }
    }
}