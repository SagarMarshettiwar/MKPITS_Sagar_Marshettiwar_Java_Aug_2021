/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractSet;

import java.util.AbstractSet;
import java.util.TreeSet;

/**
 *
 * @author SAGAR
 */
public class AbstractSetEx1 {
    public static void main(String[] args) throws Exception{
        try {
        AbstractSet<Integer> abs_set = new TreeSet<Integer>();
        abs_set.add(1);
        abs_set.add(2);
        abs_set.add(3);
        abs_set.add(4);
        abs_set.add(5);
        System.out.println("AbstractSet: "+ abs_set);
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}