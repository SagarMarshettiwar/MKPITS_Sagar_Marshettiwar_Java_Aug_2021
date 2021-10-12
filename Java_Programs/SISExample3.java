/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author SAGAR
 */
public class SISExample3 {
    public static void main(String[] args)throws IOException {
        FileInputStream f1=new FileInputStream("E:\\IOStream\\test1.txt");
        FileInputStream f2=new FileInputStream("E:\\IOStream\\test2.txt");
        FileInputStream f3=new FileInputStream("E:\\IOStream\\test2.txt");
        FileInputStream f4=new FileInputStream("E:\\IOStream\\test1.txt");
              Vector v=new Vector();
              v.add(f1);
              v.add(f2);
              v.add(f3);
              v.add(f4);
              Enumeration e=v.elements();
              SequenceInputStream sis=new SequenceInputStream(e);
              int i;
              while((i=sis.read())!=-1){
                  System.out.print((char)i);
              }
              sis.close();
              f1.close();
              f2.close();
              f3.close();
              f4.close();
    }
        
}
