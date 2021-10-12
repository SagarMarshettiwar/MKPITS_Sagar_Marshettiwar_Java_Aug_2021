/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples16;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class FDEx1 {
    public static void main(String[] args) {  
        FileDescriptor fd = null;  
        byte[] b = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58 };  
        try  {  
            FileOutputStream fos = new FileOutputStream("E:\\IOStream\\v.txt");  
            FileInputStream fis = new FileInputStream("E:\\IOStream\\v.txt");  
            fd = fos.getFD();  
            fos.write(b);  
            fos.flush();  
            fd.sync();  
            int value = 0;    
            while ((value = fis.read()) != -1) {  
                char c = (char) value;// converts bytes to char  
                System.out.print(c);  
            }  
            System.out.println("\nSync() successfully executed!!");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}
