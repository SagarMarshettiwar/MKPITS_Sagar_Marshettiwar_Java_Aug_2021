/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractQueuList;

import java.util.AbstractSequentialList;
import java.util.LinkedList;

/**
 *
 * @author SAGAR
 */
public class AbstractQueuListEx2 {
    public static void main(String args[]){
        AbstractSequentialList<String> absqlist = new LinkedList<String>();
        absqlist.add("Geeks");
        absqlist.add("for");
        absqlist.add("Geeks");
        absqlist.add("10");
        absqlist.add("20");
        System.out.println("AbstractSequentialList: "+ absqlist);
        absqlist.remove(3);
        System.out.println("Final List: "+ absqlist);
        System.out.println("The element is: "+ absqlist.get(2));
    }
}
