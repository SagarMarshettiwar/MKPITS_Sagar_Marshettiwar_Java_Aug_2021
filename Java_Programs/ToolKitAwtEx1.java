/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class ToolKitAwtEx1 extends Frame implements ActionListener{
    ToolKitAwtEx1(){
        Button b=new Button("beep");  
        b.setBounds(50,100,60,30);  
        add(b);
        b.addActionListener(this);
        setSize(300,300);  
        setLayout(null);  
        setVisible(true);
    }
        public void actionPerformed(ActionEvent e){
            Toolkit.getDefaultToolkit().beep();
        }
        public static void main(String[] args) {
        ToolKitAwtEx1 t=new ToolKitAwtEx1();
    }
}