/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples;

import java.io.FileOutputStream;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class IOEx3 {
    public static void main(String[] args) {
        try{
            FileOutputStream fout=new FileOutputStream("E:\\IOStream\\test2.txt");
            Scanner s=new Scanner(System.in);
            System.out.println("Enter name");
            //String str="name";
            String name=s.nextLine();
            byte b[]=name.getBytes();
            fout.write(b);
            fout.close();
            System.out.println("Success");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}