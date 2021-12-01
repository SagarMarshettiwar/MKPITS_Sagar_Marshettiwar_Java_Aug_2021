/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import javax.swing.*;
/**
 *
 * @author SAGAR
 */
public class SwingEx1 extends JFrame  {
    SwingEx1(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        
        JButton b=new JButton("click");
        b.setBounds(30,100,100, 40);
        add(b);
    }
    public static void main(String[] args) {
        SwingEx1 s=new SwingEx1();
    }
}
