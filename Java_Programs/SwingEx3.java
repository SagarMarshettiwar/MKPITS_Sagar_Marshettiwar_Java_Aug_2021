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
public class SwingEx3 extends JFrame implements ActionListener{
    JTextField tf;
    JButton b;
    SwingEx3(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        tf=new JTextField();
        tf.setBounds(50,50,150,20);
        add(tf);
        
        b=new JButton("Click Here");  
        b.setBounds(50,100,95,30);
        add(b);
        b.addActionListener(this);
    }
    public static void main(String[] args) {
        SwingEx3 s=new SwingEx3();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tf.setText("hello");
    }
}
