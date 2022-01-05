/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatSystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class ChatClient1 {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("localhost",6000);
        System.out.println("Client send request");
        String s1=" ",s2=" ";
        for( ; ; ){
            DataOutputStream dos=new DataOutputStream(s.getOutputStream());
            DataInputStream dis=new DataInputStream(s.getInputStream());
            Scanner sc=new Scanner(System.in);
            s1=sc.nextLine();
            dos.writeUTF(s1);
            s2=dis.readUTF();
            System.out.println("Server Says = >"+s2);
            if(s1.equals("stop")){
                dos.flush();
                dos.close();
                s.close();
            }     
        } 
    }
}
