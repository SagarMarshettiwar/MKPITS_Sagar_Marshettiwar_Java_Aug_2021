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
public class AWTex4 extends Frame implements ActionListener{
    Label l1,l2,l3;
    TextField t1,t2;
    Button b1,b2,b3;
    AWTex4(){
        setSize(300,300);
        setVisible(true);
        setLayout(null);
         
        l1=new Label("Enter first number");
        l1.setBounds(20,50,100,25);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(130,50,100,25);
        add(t1);
        
        l3=new Label("Enter Second number");
        l3.setBounds(20,100,100,25);
        add(l3);
        
        t2=new TextField();
        t2.setBounds(130,100,100,25);
        add(t2);
        
        l2=new Label("Answer : ");
        l2.setBounds(20,180,100,25);
        add(l2);
        
        b1=new Button("Add");
        b1.setBounds(20,150,40,20);
        add(b1);
        b1.addActionListener(this);
        
        b2=new Button("Sub");
        b2.setBounds(80,150,40,20);
        add(b2);
        b2.addActionListener(this);
        
        b3=new Button("Mul");
        b3.setBounds(150,150,40,20);
        add(b3);
        b3.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int num1=Integer.parseInt(t1.getText());
        int num2=Integer.parseInt(t2.getText());
       if (e.getSource()==b1){
            int result1=num1+num2;
            l2.setText("Addition = "+result1);
       }else if(e.getSource()==b2){
            int result2=num1-num2;
            l2.setText("Substraction = "+result2);
       }else if(e.getSource()==b3){
            int result3=num1*num2;
            l2.setText("Multiplication = "+result3);        
       }      
   }
    public static void main(String[] args) {
        AWTex4 a=new AWTex4();
    }
}
