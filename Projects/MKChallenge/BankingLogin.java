/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class BankingLogin extends JFrame implements ActionListener{
    JLabel l1,l2,l3;
    JTextField tf1;
    JPasswordField pf1;
    JButton b1;
    public BankingLogin() {
        setSize(400,400);
        setLayout(null);
        setVisible(true);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         
        l1=new JLabel("Username");
        l1.setBounds(20,50,80,20);
        add(l1);
        
        tf1=new JTextField();
        tf1.setBounds(100,50,80,20);
        add(tf1);
        
        l2=new JLabel("Password");
        l2.setBounds(20,100,80,20);
        add(l2);
        
        pf1=new JPasswordField();
        pf1.setBounds(100,100,80,20);
        add(pf1);
        
        b1=new JButton("login");
        b1.setBounds(150,150,90,30);
        add(b1);
        b1.addActionListener(this);
        
        l3=new JLabel("Status");
        l3.setBounds(20,200,80,20);
        add(l3);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            if(tf1.getText().equals("sagar") && pf1.getText().equals("root")){
                l3.setText("Welcome ="+tf1.getText());
                JOptionPane.showMessageDialog(this,"Welcome :"+tf1.getText());
                new BankMenu();
                dispose();
            }else{
                l3.setText("invalid user");
                JOptionPane.showMessageDialog(this,"Invalid User :");
            }
       }
    public static void main(String[] args) {
        BankingLogin b=new BankingLogin();
    }
}
    
