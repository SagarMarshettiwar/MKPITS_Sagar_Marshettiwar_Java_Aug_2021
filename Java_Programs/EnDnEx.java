/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncoderDecoder;

import java.util.Base64;

/**
 *
 * @author SAGAR
 */
public class EnDnEx {
    public static void main(String[] args) {
        String name="sagar@123";
        byte[] encode=Base64.getEncoder().encode(name.getBytes());
        System.out.println(encode);
        
        byte [] decode=Base64.getDecoder().decode(encode);
        System.out.println(new String(decode));
        
    }
}
