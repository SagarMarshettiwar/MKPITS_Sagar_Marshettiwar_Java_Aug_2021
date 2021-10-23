/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;
import java.io.*;
/**
 *
 * @author SAGAR
 */
public class ArrListEx13 {
    public static void main(String[] args) {
        ArrayList<String>al=new ArrayList<String>();
        al.add("Sagar");
        al.add("Ankita");
        al.add("Dolly");
        al.add("Ameya");
        try{//Serializzation
            FileOutputStream fout=new FileOutputStream("E:\\IOStream\\ArrayEx.txt");
            ObjectOutputStream oos=new ObjectOutputStream(fout);
            oos.writeObject(al);
            fout.close();
            oos.close();
            //Deserialization
            FileInputStream fis=new FileInputStream("E:\\IOStream\\ArrayEx.txt");
            ObjectInputStream ois=new ObjectInputStream(fis);
            ArrayList list=(ArrayList)ois.readObject();
            System.out.println(list);
        }catch(Exception e){
            System.out.println(e);            
        }
    }
}