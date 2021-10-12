/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples2;

import java.io.FileInputStream;
import java.io.SequenceInputStream;

/**
 *
 * @author SAGAR
 */
public class SISExamples {
    public static void main(String[] args) {
        try{
            FileInputStream input1=new FileInputStream("E:\\IOStream\\test1.txt");
            FileInputStream input2=new FileInputStream("E:\\IOStream\\test2.txt");
            SequenceInputStream sis=new SequenceInputStream(input1,input2);
            int i;
            while((i=sis.read())!=-1){
                System.out.print((char)i);
            }
            sis.close();
            input1.close();
            input2.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}    