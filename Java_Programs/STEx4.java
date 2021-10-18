/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringTokenizer;

import java.util.StringTokenizer;

/**
 *
 * @author SAGAR
 */
public class STEx4 {    
    public static void main(String args[]){    
        StringTokenizer st = new StringTokenizer("Hello everyone I am a Java developer"," ");    
        while (st.hasMoreElements())   
        {    
            System.out.println(st.nextToken());    
        }        
    }
}