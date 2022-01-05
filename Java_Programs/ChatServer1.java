/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatSystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class ChatServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(6000);
        System.out.println("Ready server");
        Socket s=ss.accept();
        System.out.println("Client is connected");
        String s1=" ",s2=" ";
        for( ; ; ){
            DataOutputStream dos=new DataOutputStream(s.getOutputStream());
            DataInputStream dis=new DataInputStream(s.getInputStream());
            Scanner sc=new Scanner(System.in);
            s2=sc.nextLine();
            dos.writeUTF(s2);
            s1=dis.readUTF();
            System.out.println("Client Says = >"+s1);
            if(s2.equals("stop")){
                dos.flush();
                dos.close();
                s.close();
            }     
        } 
    }
}
