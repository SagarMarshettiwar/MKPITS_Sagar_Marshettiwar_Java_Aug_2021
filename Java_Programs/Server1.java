/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networkpack;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author SAGAR
 */
public class Server1 {
    public static void main(String[] args){  
    try{  
        ServerSocket ss=new ServerSocket(6666);  
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());  
        String  str=(String)dis.readUTF();  
        System.out.println("message= "+str);  
        ss.close();  
        }catch(Exception e){System.out.println(e);}  
    }  
}  
