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
public class StringEx12 {
    public static void main(String[] args) {
        String s="My.name.is.sagar";
        String a[]=s.split(".",10);
        for (String g:a){
            System.out.println(g);
        }
    }
}
