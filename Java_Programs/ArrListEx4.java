/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;

/**
 *
 * @author SAGAR
 */
public class ArrListEx4 {
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();
        list.add("Mango");
        list.add("Apple");
        list.add("Banana");
        list.add("Chiku");
        System.out.println("Element :"+list.get(1));
        list.set(1, "Sagar");
        for(String i:list){
            System.out.println(i);
        }
    }
}