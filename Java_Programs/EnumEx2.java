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
enum days1 {  
  SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY  
} 
public class EnumEx2 {
    public static void main(String[] args) {  
        Set<days> set1 = EnumSet.allOf(days.class);  
        System.out.println("Week Days:"+set1);  
        Set<days> set2 = EnumSet.noneOf(days.class);  
        System.out.println("Week Days:"+set2);     
    }  
} 
