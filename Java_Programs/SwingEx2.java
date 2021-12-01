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
public class SwingEx2 {
    public static void main(String[] args) {
        JFrame f=new JFrame();
        
        JButton b=new JButton("press");
        b.setBounds(10,100,100,40);
        f.add(b);
        
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
    }
}
