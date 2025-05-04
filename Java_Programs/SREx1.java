/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples14;

import java.io.StringReader;

/**
 *
 * @author SAGAR
 */
public class SREx1 {
    public static void main(String[] args) throws Exception {  
        String srg = "Hello Java!! \nWelcome to Javatpoint.";  
        StringReader reader = new StringReader(srg);  
        int k=0;  
            while((k=reader.read())!=-1){  
                System.out.print((char)k);  
            }  
    }
}
