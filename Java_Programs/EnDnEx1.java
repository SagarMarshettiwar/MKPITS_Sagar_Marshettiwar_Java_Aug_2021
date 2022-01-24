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
class Encoder{
    byte[] encFun(String name){
        byte[] encode=Base64.getEncoder().encode(name.getBytes());
        return encode;
    }
    String defun(byte[] data){
        byte[] decode =Base64.getDecoder().decode(data);
        return (new String(decode));
    }
}
public class EnDnEx1 {
    public static void main(String[] args) {
        Encoder e=new Encoder();
        byte[] Eans=e.encFun("sagar@123");
        System.out.println(" EncFunc Ans ="+Eans);
        String Dans=e.defun(Eans);
        System.out.println("DncFunc Ans="+Dans);
    }
}
