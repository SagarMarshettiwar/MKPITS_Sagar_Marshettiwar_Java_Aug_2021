/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples14;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class SWEx1 {
    public static void main(String[] args) throws IOException {  
        char[] ary = new char[512];  
        StringWriter writer = new StringWriter();  
        FileInputStream input = null;  
        BufferedReader buffer = null;  
        input = new FileInputStream("E:\\IOStream\\r.txt");  
        buffer = new BufferedReader(new InputStreamReader(input));  
        int x;  
        while ((x = buffer.read(ary)) != -1) {  
                   writer.write(ary, 0, x);  
        }  
        System.out.println(writer.toString());        
        writer.close();  
        buffer.close();  
    }
}