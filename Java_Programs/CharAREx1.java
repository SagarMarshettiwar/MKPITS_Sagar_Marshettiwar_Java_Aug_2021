/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples10;

import java.io.CharArrayReader;

/**
 *
 * @author SAGAR
 */
public class CharAREx1 {
    public static void main(String[] args)throws Exception{
        char[] arr={'s','a','g','a','r'};
        CharArrayReader r=new CharArrayReader(arr);
        int k=0;
        while((k=r.read())!=-1){
            char ch=(char)k;
            System.out.print(ch+":");
            System.out.println(k);
        }
    }
}