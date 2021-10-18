/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringBuffer;

/**
 *
 * @author SAGAR
 */
public class StringBuffEx7 {
    public static void main(String[] args) {
        String s1="";
        String s2="";
        StringBuffer sb=new StringBuffer("SWJWS");
        s1=sb.toString();
        System.out.println(s1);
        sb.reverse();
        s2=sb.toString();
        System.out.println(s2);
        if(s1.equals(s2)){
            System.out.println("palindrome");
        }else{
            System.out.println("Not");
        }
    }
}