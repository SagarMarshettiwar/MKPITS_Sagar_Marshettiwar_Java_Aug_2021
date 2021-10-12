/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples12;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author SAGAR
 */
public class OSWEx1 {
    public static void main(String[] args) {  
        try {  
            OutputStream outputStream = new FileOutputStream("E:\\IOStream\\s.txt");  
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);  
            outputStreamWriter.write("Hello World");  
            outputStreamWriter.close();  
        }catch (Exception e) {  
                e.getMessage();  
        }  
    } 
}
