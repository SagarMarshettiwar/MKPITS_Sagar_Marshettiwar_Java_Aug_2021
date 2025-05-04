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
public class AbstractCollectionEx1 {
    public static void main(String[] args){
        AbstractCollection<Object> abs = new ArrayList<Object>();
        abs.add("Welcome");
        abs.add("To");
        abs.add("mkpits");
        abs.add("software");
        abs.add("solutions");
        System.out.println("AbstractCollection: "+ abs);
    }
}
