/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples11;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author SAGAR
 */
public class PWEx1 {
    public static void main(String[] args) throws Exception {  
        PrintWriter writer = new PrintWriter(System.out);    
        writer.write("hello Every one");        
        writer.flush();  
        writer.close();
        FileOutputStream s=new FileOutputStream("E:\\IOStream\\r.txt");
        PrintWriter writer1 = new PrintWriter(s);  
        writer1.write("Nice to meet you");                                                   
        writer1.flush();  
        writer1.close();  
    }     
}  