/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples3;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class BAOSEx1 {
    public static void main(String[] args)throws Exception {
        FileOutputStream fout=new FileOutputStream("E:\\IOStream\\c.txt");
        FileOutputStream fout1=new FileOutputStream("E:\\IOStream\\d.txt");
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        bout.write(65);
        bout.writeTo(fout);
        bout.writeTo(fout1);
        bout.close();
        System.out.println("success");
    }
}