/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples7;

import java.io.FileWriter;
import java.io.Writer;

/**
 *
 * @author SAGAR
 */
public class WriterEx2 {
    public static void main(String[] args) {
        try{
            Writer w=new FileWriter("E:\\IOStream\\m.txt");
            w.write("asdfghjiuytrfvbnm");
            w.close();
            System.out.println("Done");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}