/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 *
 * @author SAGAR
 */
public class BINSex1 {
    public static void main(String[] args) {
        try{
            FileInputStream fis=new FileInputStream("E:\\IOstream\\a.txt");
            BufferedInputStream bis=new BufferedInputStream(fis);
            int i;
            while((i=bis.read())!=-1){
                System.out.print((char)i);
            }
            bis.close();
            fis.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}