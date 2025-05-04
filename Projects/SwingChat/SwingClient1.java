/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class SwingClient1 extends JFrame implements ActionListener{
    JLabel l1;
    JTextArea ta;
    JTextField tf1;
    JToggleButton b1;
    Socket s;
    String s1;
    String s2;
    DataOutputStream dos;
    DataInputStream dis;
    public SwingClient1() {
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Client");
        
        l1=new JLabel("Write Messege");
        l1.setBounds(30,30,100,40);
        add(l1);
        
        tf1=new JTextField();
        tf1.setBounds(30,300,250,20);
        add(tf1);
        
        ta=new JTextArea();
        ta.setBounds(30,100,270,180);
        add(ta);
        
        b1=new JToggleButton("Send");
        b1.setBounds(280,300,80,30);
        add(b1);
        b1.addActionListener(this);
        
            try {
                s=new Socket("localhost",6000);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            System.out.println("Client send request");
            
            s2=" "; 
            try{
                dis=new DataInputStream(s.getInputStream());
                s2=dis.readUTF();
                System.out.println("Server"+s2);
                ta.setText("Server Says=>"+s2.toString());
            }catch(Exception ex1){
                System.out.println(ex1);
            }
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
       try{ 
            s1=tf1.getText();
                dos=new DataOutputStream(s.getOutputStream());
                dos.writeUTF(s1);
                if(s1.equals("stop")){
                    dos.flush();
                    dos.close();
                    s.close();
                }  
        }catch(IOException e1){
                    System.out.println(e1);
        }
    }     
        public static void main(String[] args) {
            SwingClient1 s=new SwingClient1();
    }
}