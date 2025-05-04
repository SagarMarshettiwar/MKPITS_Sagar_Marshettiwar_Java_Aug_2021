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
class CustomFilterReader extends FilterReader {  
    CustomFilterReader(Reader in) {  
        super(in);  
    }  
    public int read() throws IOException {  
        int x = super.read();  
        if ((char) x == ' ')  
            return ((int) '?');  
        else  
            return x;  
    }  
}  
public class FREx1 {  
    public static void main(String[] args) {  
        try  {  
            Reader reader = new FileReader("E:\\IOStream\\t.txt");  
            CustomFilterReader fr = new CustomFilterReader(reader);  
            int i;  
            while ((i = fr.read()) != -1) {  
                System.out.print((char) i);  
            }  
            fr.close();  
            reader.close();  
        } catch (Exception e) {  
            e.getMessage();  
        }  
    }  
}