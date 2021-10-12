/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples8;

import java.io.FileReader;

/**
 *
 * @author SAGAR
 */
public class FREx1 {
    public static void main(String args[])throws Exception{    
          FileReader fr=new FileReader("E:\\IOStream\\k.txt");    
          int i;    
          while((i=fr.read())!=-1)    
          System.out.print((char)i);    
          fr.close();    
    } 
}