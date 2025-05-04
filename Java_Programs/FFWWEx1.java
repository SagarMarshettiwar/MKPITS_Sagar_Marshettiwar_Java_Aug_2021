/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples15;

import java.io.*;

/**
 *
 * @author SAGAR
 */
class CustomFilterWriter extends FilterWriter {  
    CustomFilterWriter(Writer out) {  
        super(out);  
    }  
    public void write(String str) throws IOException {  
        super.write(str.toLowerCase());  
    }  
}  
public class FWEx1 {
    public static void main(String[] args) {  
        try {  
            FileWriter fw = new FileWriter("E:\\IOStream\\t.txt");   
            CustomFilterWriter filterWriter = new CustomFilterWriter(fw);             
            filterWriter.write("I LOVE MY COUNTRY");  
            filterWriter.close();  
            FileReader fr = new FileReader("E:\\IOStream\\t.txt");  
            BufferedReader bR = new BufferedReader(fr);  
            int k;  
            while ((k = bR.read()) != -1) {  
                System.out.print((char) k);  
            }  
            bR.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}