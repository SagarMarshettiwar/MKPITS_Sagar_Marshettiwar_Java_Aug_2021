/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractList;
import java.util.*;
/**
 *
 * @author SAGAR
 */
public class AbstractListEx2 {
    public static void main(String args[]){
        AbstractList<String> list = new LinkedList<String>();
        list.add("Geeks");
        list.add("for");
        list.add("mkpits");
        list.add("10");
        list.add("20");
        System.out.println("AbstractList: " + list);
        list.remove(3);
        System.out.println("Final AbstractList: " + list);
        int lastindex = list.lastIndexOf("for");
         int lastindex1 = list.lastIndexOf("A");
        System.out.println("Last index of (for): "+ lastindex+"   last index of (A): "+lastindex1);
    }
}
