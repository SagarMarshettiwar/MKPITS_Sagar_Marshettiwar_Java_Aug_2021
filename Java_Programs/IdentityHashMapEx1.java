/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IdentityHashMap;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author SAGAR
 */
public class IdentityHashMapEx1 {
    public static void main(String args[]) {
        Map<String, String> ihm = new IdentityHashMap<>();
        ihm.put("Priya", "10100");
        ihm.put("Shravan", "23290");
        ihm.put(new String("Shreya"), "23330");
        ihm.put("Anu", "45000");
        ihm.put("Nirnay", "67700");
        Set set = ihm.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        System.out.println();
        System.out.println("Size of IdentityHashMap is: " + ihm.size());
    }
}
