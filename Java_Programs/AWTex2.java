/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples2;

import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class AWTex2 extends Frame implements ActionListener{
    Label l1,l2;
    TextField t1;
    Button b1;
    AWTex2(){
        setSize(300,300);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Enter number");
        l1.setBounds(20,50,100,25);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(130,50,100,25);
        add(t1);
        
        l2=new Label("square : ");
        l2.setBounds(20,150,100,25);
        add(l2);
        
        b1=new Button("ok");
        b1.setBounds(100,100,100,25);
        add(b1);
        b1.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int num=Integer.parseInt(t1.getText());
        int SQ=num*num;
        l2.setText("square = "+SQ);
    }
    public static void main(String[] args) {
        AWTex2 a=new AWTex2();
    }
}