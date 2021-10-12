/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples3;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author SAGAR
 */
public class BAOSEx2 {
    public static void main(String[] args)throws Exception {
        FileOutputStream fout=new FileOutputStream("E:\\IOStream\\e.txt");
        FileOutputStream fout1=new FileOutputStream("E:\\IOStream\\f.txt");
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        String s="Sagar";
        byte b[]=s.getBytes();
        bout.write(b);
        bout.writeTo(fout);
        bout.writeTo(fout1);
        bout.close();
        System.out.println("Success");
    }
}