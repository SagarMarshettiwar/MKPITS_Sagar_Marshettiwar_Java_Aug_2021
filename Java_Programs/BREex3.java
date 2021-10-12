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
public class BREex3 {
    public static void main(String args[])throws Exception{             
        InputStreamReader r=new InputStreamReader(System.in);    
        BufferedReader br=new BufferedReader(r); 
        String name="";
        while(!name.equals("stop")){    
            System.out.println("Enter data: ");    
            name=br.readLine();    
            System.out.println("data is: "+name);    
        }              
        br.close();    
        r.close();    
    }
}    