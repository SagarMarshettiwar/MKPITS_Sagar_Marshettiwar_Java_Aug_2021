/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class SwingEx4 extends JFrame implements ActionListener {
    JLabel l1,l2,l3;
    JTextField t1,t2;
    JButton b1;
    JPasswordField p1;
    SwingEx4(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        l1= new JLabel("username");
        l1.setBounds(10,50,100,20);
        add(l1);

        t1= new JTextField();
        t1.setBounds(120,50,100,20);
        add(t1);

        l2= new JLabel("password");
        l2.setBounds(10,100,100,20);
        add(l2);

        p1= new JPasswordField();
        p1.setBounds(120,100,100,20);
        add(p1);

        JButton b1=new JButton("save"); 
        b1.setBounds(100,150,100,20);
        add(b1);
        b1.addActionListener(this);
        
        l3= new JLabel("status :");
        l3.setBounds(10,200,100,20);
        add(l3);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(t1.getText().equals("Admin")&&p1.getText().equals("Admin")){
                l3.setText("Login Success");
        }else{
                l3.setText("Invalid Password");
        }
    }
    public static void main(String[] args) {
        SwingEx4 a=new SwingEx4();
    }
}
