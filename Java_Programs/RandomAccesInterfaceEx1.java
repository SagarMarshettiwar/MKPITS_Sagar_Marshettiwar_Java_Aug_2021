/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomAccessInterface;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author SAGAR
 */
public class RandomAccesInterfaceEx1 {
    public static void main(String args[]){
        ArrayList<String> list=new ArrayList<String> ();
            list.add("apple");
            list.add("mango");
            list.add("banana");
            for (int i=0, n=list.size(); i < n; i++)
                System.out.println( list.get(i));
            for (Iterator i=list.iterator(); i.hasNext(); )
                System.out.println( i.next());

    }
}
