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
public class MapInterfaceEx4 {
    public static void main(String[] args) {
        Map map=new HashMap();
        map.put(100,"Amit");    
        map.put(101,"Vijay");    
        map.put(102,"Rahul");
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(System.out::println);
    }
}
