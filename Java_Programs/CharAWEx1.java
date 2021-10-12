/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples10;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class CharAWEx1 {
    public static void main(String[] args)throws Exception{
        CharArrayWriter w=new CharArrayWriter();
        w.write("Welcome to mkpits");
        FileWriter f1=new FileWriter("E:\\IOStream\\m.txt");    
        FileWriter f2=new FileWriter("E:\\IOStream\\n.txt");    
        FileWriter f3=new FileWriter("E:\\IOStream\\o.txt");    
        FileWriter f4=new FileWriter("E:\\IOStream\\p.txt");    
        w.writeTo(f1);    
        w.writeTo(f2);    
        w.writeTo(f3);    
        w.writeTo(f4);    
        f1.close();    
        f2.close();    
        f3.close();    
        f4.close();    
        System.out.println("Success...");    
    }    
} 