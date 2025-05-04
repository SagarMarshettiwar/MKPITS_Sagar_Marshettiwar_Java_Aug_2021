package IOExamples;


import java.io.FileOutputStream;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAGAR
 */
public class IOExx3 {
    public static void main(String[] args) {
        try{
            FileOutputStream fout=new FileOutputStream("E:\\IOStream\\Test2.2.txt");
            Scanner s=new Scanner(System.in);
            System.out.println("Enter your name");
            String str="name";
            str=s.nextLine();
            byte b[]=str.getBytes();
            fout.write(b);
            fout.close();
            System.out.println("Success");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
