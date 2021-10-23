/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DequeInterface;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author SAGAR
 */
public class DequeEx1 {
        public static void main(String[] args) {    
                Deque<String> deque = new ArrayDeque<String>();  
                deque.add("Ravi");    
                deque.add("Vijay");     
                deque.add("Ajay");    
                //Traversing elements  
                for (String str : deque) {  
                System.out.println(str);  
                }  
        }  
}