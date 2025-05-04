/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples1;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author SAGAR
 */
public class BOSEx1 {
    public static void main(String[] args) {
        try{
            FileOutputStream fos=new FileOutputStream("E:\\IOStream\\a.txt");
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            String s="Sagar";
            byte b[]=s.getBytes();
            bos.write(b);
            bos.flush();
            bos.close();
            fos.close();
            System.out.println("Success");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
