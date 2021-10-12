/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples3;

import java.io.*;

/**
 *
 * @author SAGAR
 */
public class BAISEx1 {
    public static void main(String[] args)throws IOException  {
        byte a[]={35,36,37,38};
        ByteArrayInputStream byt=new ByteArrayInputStream(a);
        int i=0;
        while((i=byt.read())!=-1){
            System.out.println(i+"="+(char)i);
        }
    }
}
