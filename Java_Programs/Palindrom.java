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
public class Palindrom {
    public static void main(String[] args) {
        String a="madam";
        String b="";
       //int p=a.length();
        for(int i=a.length()-1;i>=0;i--){
            b=b+a.charAt(i);
        }
        if(a.equals(b)){
            System.out.println("String Palindrom");
        }else{
            System.out.println("not");
        }
    }
}
