/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples5;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class FOSEx1 {
    public static void main(String[] args)throws IOException {
        FileOutputStream file=new FileOutputStream("E:\\IOStream\\j.txt");
        FilterOutputStream filter=new FilterOutputStream(file);
        String s="Sagar Marshettiwar";
        byte b[]=s.getBytes();
        filter.write(b);
        file.close();
        filter.flush();
        filter.close();
        System.out.println("success");
    }
}
