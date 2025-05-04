/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class AWTex1 extends Frame implements ActionListener{
    AWTex1(){
        setSize(300,300);
        setVisible(true);
        setLayout(null);
        
        Button b=new Button("ok");
           b.setBounds(100,50,100,25);
           add(b);
           b.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Hello from Button");
    }
    public static void main(String[] args) {
        AWTex1 a=new AWTex1();
    }
}
