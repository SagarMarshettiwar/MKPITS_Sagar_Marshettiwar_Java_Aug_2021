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
public class BREx1 {
    public static void main(String args[])throws Exception{    
        FileReader fr=new FileReader("E:\\IOStream\\l.txt");    
        BufferedReader br=new BufferedReader(fr);    
        int i;    
        while((i=br.read())!=-1){  
        System.out.print((char)i);  
        }  
        br.close();    
        fr.close();    
    } 
}