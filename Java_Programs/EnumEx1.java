/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

import java.util.*;

/**
 *
 * @author SAGAR
 */
enum days{
    SUNDAY,MONDAY,TUESDAY,WEDNESDAY, THURSDAY, FRIDAY, SATURDAY 
}
public class EnumEx1 {
    public static void main(String[] args) {
        Set<days> set = EnumSet.of(days.TUESDAY, days.WEDNESDAY);  
        Iterator<days> itr=set.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}