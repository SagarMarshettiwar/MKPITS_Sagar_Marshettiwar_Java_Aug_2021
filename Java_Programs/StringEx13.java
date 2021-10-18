/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringExamples;

/**
 *
 * @author SAGAR
 */
public class StringEx13 {
    public static void main(String[] args) {
        String s="My.name.is.sagar";
        int i=s.indexOf('n');
        System.out.println(i);
        int j=s.indexOf("is",2 );
        System.out.println(j);
    }
}