/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author SAGAR
 */
public class ArrListEx6 {
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();  
           list.add("Ravi");  
           list.add("Vijay");  
           list.add("Ravi");  
           list.add("Ajay");
           System.out.println("ListIteratir");
           ListIterator<String> li=list.listIterator(list.size());
           while(li.hasPrevious()){
               String str=li.previous();
               System.out.println(str);
           }
           System.out.println("======================================");
           System.out.println("For Loop");
           for(int i=0;i<list.size();i++){
               System.out.println(list.get(i));
           }
    }
}