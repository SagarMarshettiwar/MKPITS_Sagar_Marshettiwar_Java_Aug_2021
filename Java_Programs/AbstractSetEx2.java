/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractSet;

import java.util.*;

/**
 *
 * @author SAGAR
 */
public class AbstractSetEx2 {
    public static void main(String[] args) throws Exception{
        try {
        AbstractSet<Integer> abs_set = new TreeSet<Integer>();
            abs_set.add(1);
            abs_set.add(2);
            abs_set.add(3);
            abs_set.add(4);
            abs_set.add(5);
        System.out.println("AbstractSet before "+ abs_set);
        Collection<Integer> arrlist2 = new ArrayList<Integer>();
            arrlist2.add(1);
            arrlist2.add(2);
            arrlist2.add(3);
        System.out.println("Collection Elements to be removed : "+ arrlist2);
        abs_set.removeAll(arrlist2);
        System.out.println("AbstractSet after "+ abs_set);
        }
        catch (NullPointerException e) {
            System.out.println("Exception thrown : " + e);
        }
    }
}
