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
public class AbstractListEx1 {
    public static void main(String args[]){
        AbstractList<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("for");
        list.add("mkpits");
        list.add("10");
        list.add("20");
        System.out.println("AbstractList:" + list);
    }
}
