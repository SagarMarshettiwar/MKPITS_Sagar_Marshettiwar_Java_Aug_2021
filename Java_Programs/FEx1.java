/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples2;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class FEx1 {
    public static void main(String[] args) {  
        try {  
            File file = new File("E:\\IOStream\\u.txt");  
            if (file.createNewFile()) {  
                System.out.println("New File is created!");  
            } else {  
                System.out.println("File already exists.");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}