/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples9;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class BWEx1 {  
    public static void main(String[] args) throws Exception {     
        FileWriter writer = new FileWriter("E:\\IOStream\\l.txt");  
        BufferedWriter buffer = new BufferedWriter(writer);  
        buffer.write("Welcome to Mkpits.");  
        buffer.close();  
        System.out.println("Success");  
    }
}