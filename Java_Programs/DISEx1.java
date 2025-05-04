package IOExamples4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;

/**
 *
 * @author SAGAR
 */
public class DISEx1 {
    public static void main(String[] args)throws IOException {
         FileInputStream input = new FileInputStream("e:\\IOStream\\test1.txt"); 
         DataInputStream dis=new DataInputStream(input);
         int count=input.available();
         byte b[]=new byte[count];
         dis.read(b);
         for(byte bt:b){
             System.out.print((char)bt+"-");
         }
    }
}    