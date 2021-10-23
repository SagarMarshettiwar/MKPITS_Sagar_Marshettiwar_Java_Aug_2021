/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractCollection;

import java.util.*;

/**
 *
 * @author SAGAR
 */
public class AbstractCollectionEx2 {
    public static void main(String args[]){
        AbstractCollection<String> abs1 = new TreeSet<String>();
        abs1.add("Hello");
        abs1.add("To");
        abs1.add("sagar");
        abs1.add("4");
        abs1.add("sagar");
        abs1.add("TreeSet");
        //abs1.remove("To");
        System.out.println("AbstractCollection 1: "+ abs1);
        AbstractCollection<String> abs2 = new TreeSet<String>();
        System.out.println("AbstractCollection 2: "+ abs2);
        abs2.addAll(abs1);
        System.out.println("AbstractCollection 2 updated: "+ abs2);
        abs1.clear();
        System.out.println("Is the collection empty? "+ abs1.isEmpty());

    }
}
