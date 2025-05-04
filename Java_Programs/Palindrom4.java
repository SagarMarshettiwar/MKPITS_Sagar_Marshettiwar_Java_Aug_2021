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
public class Palindrom4 {
    public static void main(String[] args) {
        String a="madam";
        String b="";
        StringBuffer sb =new StringBuffer(a);
        sb.reverse();
        //b=sb.toString();
        if(a.contentEquals(sb)){
            System.out.println("palindrom");
        }else{
            System.out.println("not");
        }
    }
}
