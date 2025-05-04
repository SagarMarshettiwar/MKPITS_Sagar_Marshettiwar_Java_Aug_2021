/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mkpits;

/**
 *
 * @author SAGAR
 */
public class Palindrom2 {
    public static void main(String[] args) {
        String a="madam";
        String b="";
        StringBuilder sb =new StringBuilder(a);
        sb.reverse();
        b=sb.toString();
        if(a.equals(b)){
            System.out.println("palindrom");
        }else{
            System.out.println("not");
        }
    }
}
