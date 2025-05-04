/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class JpannelSwingEx1 extends JFrame{
    JPanel p1;
    JButton b1;
    JpannelSwingEx1(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        p1=new JPanel();
        p1.setBounds(10,100,200,200);
        p1.setBackground(Color.red);
        add(p1);
        
        b1=new JButton("press");
        b1.setBounds(30,10,20,40);
        add(b1);
        p1.add(b1);
    }
    public static void main(String[] args) {
        JpannelSwingEx1 p=new JpannelSwingEx1();
    }
}
