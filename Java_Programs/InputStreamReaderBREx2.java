/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples9;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class InputStreamReaderBREx2 {
    public static void main(String args[])throws Exception{             
        InputStreamReader r=new InputStreamReader(System.in);    
        BufferedReader br=new BufferedReader(r);            
        System.out.println("Enter your name");    
        String name=br.readLine();    
        System.out.println("Welcome "+name);    
    } 
}