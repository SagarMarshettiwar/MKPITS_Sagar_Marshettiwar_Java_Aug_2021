/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples7;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class ReaderEx1 {
    public static void main(String[] args) {  
        try {  
            Reader reader = new FileReader("E:\\IOStream\\k.txt");  
            int data=reader.read();
            while (data != -1) {  
                System.out.print((char) data);  
                data = reader.read();  
            }
//                int i;
//                while((i=reader.read())!=-1){
//                    System.out.print((char)i);
//                }
            reader.close();  
        } catch (Exception ex) {  
            System.out.println(ex.getMessage());  
        }  
    }  
}