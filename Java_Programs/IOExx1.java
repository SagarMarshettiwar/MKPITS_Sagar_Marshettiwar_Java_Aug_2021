/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples;

import java.io.FileOutputStream;

/**
 *
 * @author SAGAR
 */
public class IOExx1 {
    public static void main(String[] args) {
        try{
            FileOutputStream fout=new FileOutputStream("E:\\IOStream\\test0.0.txt");
                fout.write(67);
                fout.close();
                System.out.println("Success");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}