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
public class StringEx2 {
    public static void main(String[] args) {
        String s3="Message";
        System.out.println(s3.length());
        int c=s3.length();
        for(int i=0;i<c;i++){
            System.out.println(i+" "+s3.charAt(i));
        }
    }
}
