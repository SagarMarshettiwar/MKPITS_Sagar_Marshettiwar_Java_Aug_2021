/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author SAGAR
 */
public class ArrListEx5 {
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();
        list.add("Mango");
        list.add("Apple");
        list.add("Banana");
        list.add("Chiku");
        Collections.sort(list);
        for(String i:list){
            System.out.println(i);
        }
        ArrayList<Integer> list1=new ArrayList<Integer>();    
        list1.add(7);
        list1.add(3);
        list1.add(9);
        list1.add(8);
        Collections.sort(list1);
        for(Integer j:list1){
            System.out.println(j);
        }
    }
}
