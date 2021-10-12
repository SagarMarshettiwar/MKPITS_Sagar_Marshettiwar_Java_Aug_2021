/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples3;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class BAOSEx3 {
    public static void main(String[] args)throws Exception {
        FileOutputStream fout=new FileOutputStream("E:\\IOStream\\g.txt");
        FileOutputStream fout1=new FileOutputStream("E:\\IOStream\\h.txt");
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter String");
        String name=sc.nextLine();
        byte b[]=name.getBytes();
        bout.write(b);
        bout.writeTo(fout);
        bout.writeTo(fout1);
        bout.close();
        System.out.println("Success");
    }
}
