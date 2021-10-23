/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapInterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author SAGAR
 */
public class MapInterfaceEx2 {
    public static void main(String[] args) {
        Map map=new HashMap();
        map.put(1, "Amit");
        map.put(5,"Rahul");  
        map.put(2,"Jai");  
        map.put(6,"Amit");
        Set set=map.entrySet();
        Iterator itr=set.iterator();
        while(itr.hasNext()){
            Map.Entry entry=(Map.Entry)itr.next();
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }
}
