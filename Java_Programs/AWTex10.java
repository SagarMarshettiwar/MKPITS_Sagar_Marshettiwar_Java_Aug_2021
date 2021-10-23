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
public class AWTex10 extends Frame implements ActionListener{
    Label l1,l2,l3,l4,l5;
    Button b1;
    TextField t1,t2,t3,t4;
    AWTex10(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Cust Name =");
        l1.setBounds(20,50,80,20);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(100,50,70,20);
        add(t1);
        
        l2=new Label("Product name =");
        l2.setBounds(20,100,80,20);
        add(l2);
        
        t2=new TextField();
        t2.setBounds(100,100,70,20);
        add(t2);
        
        l3=new Label("Product Price =");
        l3.setBounds(20,150,80,20);
        add(l3);
        
        t3=new TextField();
        t3.setBounds(100,150,70,20);
        add(t3);
        
        l4=new Label("Quantity =");
        l4.setBounds(20,200,50,20);
        add(l4);
        
        t4=new TextField();
        t4.setBounds(100,200,70,20);
        add(t4);
        
        l5=new Label("Total amount =");
        l5.setBounds(20,250,100,20);
        add(l5);
        
        b1=new Button("Press");
        b1.setBounds(150,300, 60,20);
        add(b1);
        b1.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        String Cname=t1.getName();
        String Pname=t2.getName();
        int Price=Integer.parseInt(t3.getText());
        int Quantity=Integer.parseInt(t4.getText());
        int Amount=0;
        if(e.getSource()==b1){
            Amount=Price*Quantity;
        }
        l5.setText("Total Amount ="+Amount);
    }
    public static void main(String[] args) {
        AWTex10 a=new AWTex10();
    }
}
