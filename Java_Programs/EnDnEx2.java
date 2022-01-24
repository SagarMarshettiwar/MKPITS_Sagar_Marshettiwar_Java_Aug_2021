/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncoderDecoder;

import static EncoderDecoder.EnDnEx2.bout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 *
 * @author SAGAR
 */
class Encoder1{
    StringBuffer sb;
    byte[] encode;
    byte[] decode;
    byte[] encFun(String name){
        try{
            FileInputStream fin=new FileInputStream(name);
            BufferedInputStream bin=new BufferedInputStream(fin);
            sb=new StringBuffer();
            int i;
            while((i=bin.read())!=-1){
               sb.append((char)i);
               encode=Base64.getEncoder().encode(sb.toString().getBytes());  
            }    
            bin.close();    
            fin.close();
        }catch(Exception e){
            System.out.println(e);
        }
        //return sb.toString();
//        byte[] encode=Base64.getEncoder().encode(name.getBytes());
          return encode;
    }
    String defun(byte[] data){
        try{
            FileOutputStream fout1=new FileOutputStream("E:\\EncryptDecrypt\\String2.txt");          
            bout=new BufferedOutputStream(fout1);
            decode =Base64.getDecoder().decode(data);
            bout.write(decode);    
            bout.flush();    
            bout.close();
        }catch(Exception e1){
            System.out.println(e1);
        }
//        byte[] decode =Base64.getDecoder().decode(data);
          return (new String(decode));
    }
}   
public class EnDnEx2 {
     static BufferedOutputStream bout;
    public static void main(String[] args) {
        byte[] Eans;
        Encoder1 e=new Encoder1();
        Eans=e.encFun("E:\\EncryptDecrypt\\String.txt");
        System.out.println(" EncFunc Ans ="+Eans);
        try{
            FileOutputStream fout1=new FileOutputStream("E:\\EncryptDecrypt\\String1.txt");          
            bout=new BufferedOutputStream(fout1);    
            bout.write(Eans);    
            bout.flush();    
            bout.close();
        }catch(Exception e1){
            System.out.println(e1);
        }
        String Dans=e.defun(Eans);
        System.out.println("DncFunc Ans="+Dans);
    }
}
