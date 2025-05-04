/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples12;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class ISREx1 {
    public static void main(String[] args) {  
        try  {  
            InputStream stream = new FileInputStream("E:\\IOStream\\s.txt");  
            Reader reader = new InputStreamReader(stream);  
            int data = reader.read();  
            while (data != -1) {  
                System.out.print((char) data);  
                data = reader.read();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}