/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples2;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class AWTex8 extends Frame implements ActionListener{
    Label l1;
    TextArea ta;
    TextField t1;
    Button b1;
    AWTex8(){
        setSize(300,400);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Enter number for Table");
        l1.setBounds(20,50,150,25);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(180,50,100,25);
        add(t1);
        
        b1=new Button("Press");
        b1.setBounds(100,100,100,25);
        add(b1);
        b1.addActionListener(this);
        
        ta=new TextArea( );
        ta.setBounds(20,150,150,200);
        add(ta);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int num=Integer.parseInt(t1.getText());
        int result=0;
        StringBuilder sb=new StringBuilder();
        if(e.getSource()==b1){
            for(int i=1;i<=10;i++){
                result=num*i;
                sb.append(num + " * " + i + "=" + result +"\n");
            }
        }
        ta.setText(sb.toString());
    }
    public static void main(String[] args) {
        AWTex8 a=new AWTex8();
    }
}