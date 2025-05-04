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
public class Palindrom5 {
    public static void main(String[] args) {
        String a="nayan";
        String b="";
        for(int i=a.length()-1;i>=0;i--){
            b=b+a.charAt(i);
        }
        if(a.compareTo(b)==0)
            System.out.println("palindrom");
        else
            System.out.println("not");
    }
}
