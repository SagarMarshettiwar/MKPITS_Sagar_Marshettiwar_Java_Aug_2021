/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author SAGAR
 */
public class DisplayRecord extends JFrame{
        JTextArea ta;
    public DisplayRecord() {
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ta=new JTextArea(2,3);
        ta.setBounds(30,30,200,200);
        add(ta); 
    }
//    public static void main(String[] args) {
//        DisplayRecord dr=new DisplayRecord();
//    }
}
