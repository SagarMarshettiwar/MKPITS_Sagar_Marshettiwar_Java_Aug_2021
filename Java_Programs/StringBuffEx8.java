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
public class StringBuffEx8 {
    public static void main(String args[]){
        StringBuffer sb=new StringBuffer();
        System.out.println(sb.capacity());
        sb.append("Hello");
        System.out.println(sb.capacity());
        sb.append("java is my favourite language");
        System.out.println(sb.capacity());
    }
}
