/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples8;

import java.io.FileWriter;

/**
 *
 * @author SAGAR
 */
public class FWEx1 {
    public static void main(String args[]){    
        try{    
            FileWriter fw=new FileWriter("D:\\testout.txt");    
            fw.write("Welcome to javaTpoint.");    
            fw.close();    
        }catch(Exception e){System.out.println(e);}    
            System.out.println("Success...");    
     }    
}