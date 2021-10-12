/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOExamples4;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author SAGAR
 */
public class DOSEx1 {
    public static void main(String[] args) throws IOException {  
        FileOutputStream file = new FileOutputStream("E:\\IOStream\\i.txt");  
        DataOutputStream data = new DataOutputStream(file);  
        data.writeInt(65);
        data.writeByte(66);
        data.writeChar(67);
        data.writeDouble(68);
        data.writeFloat(69);
        data.writeLong(70);
        data.writeShort(71);
        data.flush();  
        data.close();  
        System.out.println("Succcess...");  
    }  
}