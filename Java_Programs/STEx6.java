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
public class STEx6 {
    public static void main(String args[]){    
        StringTokenizer st = new StringTokenizer("Hello Everyone Have a nice day"," ");
            System.out.println("Total number of Tokens: "+st.countTokens());    
    } 
}