/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author SAGAR
 */
public class SwingServer1 extends JFrame implements ActionListener{
    JLabel l1;
    JTextArea ta;
    JTextField tf1;
    JToggleButton b1;
    ServerSocket ss;
    Socket s;
    String s1;
    String s2;
    DataOutputStream dos;
    DataInputStream dis;
    public SwingServer1() {
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Server");
        
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
                ss = new ServerSocket(6000);
                System.out.println("Ready server");
                s=ss.accept();
            System.out.println("Client is connected");
            } catch (IOException ex) {
                Logger.getLogger(SwingServer1.class.getName()).log(Level.SEVERE, null, ex);
            }
            s1=" ";
            try{
            dis=new DataInputStream(s.getInputStream());
            s1=dis.readUTF();
            System.out.println("client"+s1);
            ta.setText("Client Says = >"+s1.toString());
            }catch(IOException ex1){
                System.out.println(ex1);
            }
    }       
    public static void main(String[] args) {
        SwingServer1 s=new SwingServer1();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        try{
            s2=tf1.getText();
            dos=new DataOutputStream(s.getOutputStream());  
            dos.writeUTF(s2);
            if(s2.equals("stop")){
                dos.flush();
                dos.close();
                s.close();
            }     
        }catch(IOException e1){
                    System.out.println(e1);
        }  
    }
}