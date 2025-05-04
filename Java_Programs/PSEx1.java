/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples11;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 *
 * @author SAGAR
 */
public class PSEx1 {
    public static void main(String[] args)throws Exception{
        FileOutputStream fout=new FileOutputStream("E:\\IOStream\\q.txt");
        PrintStream p=new PrintStream(fout);
        p.println(2016);
        p.println("hello java");
        p.println("Welcome to mkpits");
        p.close();
        int a=90;
        System.out.println(a);
        fout.close();
        System.out.println("Success");
    }
}    