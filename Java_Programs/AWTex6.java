/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples2;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class AWTex6 extends Frame implements ActionListener{
    Label l1,l2;
    TextField t1;
    Button b1;
    AWTex6(){
        setSize(300,300);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Enter number for Factorial");
        l1.setBounds(20,50,150,25);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(180,50,100,25);
        add(t1);
        
        l2=new Label("Answer : ");
        l2.setBounds(20,150,100,25);
        add(l2);
        
        b1=new Button("Press");
        b1.setBounds(100,100,100,25);
        add(b1);
        b1.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int num=Integer.parseInt(t1.getText());
        int Fact=1;
        if(e.getSource()==b1){
            for(int i=num;i>0;i--){
                Fact=Fact*i;
            }
        }
        l2.setText("Factorial= "+Fact);
    }
    public static void main(String[] args) {
        AWTex6 a=new AWTex6();
    }
}