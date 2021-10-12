/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples13;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class PBISEx1 {
    public static void main(String[] args)throws Exception{  
          String srg = "1##2#34###12";  
          byte ary[] = srg.getBytes();  
          ByteArrayInputStream array = new ByteArrayInputStream(ary);  
          PushbackInputStream push = new PushbackInputStream(array);  
          int i;        
              while((i = push.read())!= -1) {  
                  if(i == '#') {  
                      int j;  
                      if( (j = push.read()) == '#'){  
                          System.out.print("**");  
                      }else {  
                          push.unread(j);  
                          System.out.print((char)i);  
                      }  
                  }else {  
                          System.out.print((char)i);  
                  }  
             }        
    }   
}