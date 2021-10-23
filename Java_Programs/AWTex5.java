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
public class AWTex5 extends Frame implements ActionListener{
    Label l1,l2,l3,l4,l5,l6;
    TextField t1,t2,t3;
    Button b1;
    AWTex5(){
        setSize(300,400);
        setVisible(true);
        setLayout(null);
         
        l1=new Label("Enter marks s1");
        l1.setBounds(20,50,100,25);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(130,50,100,25);
        add(t1);
        
        l3=new Label("Enter marks s2");
        l3.setBounds(20,100,100,25);
        add(l3);
        
        t2=new TextField();
        t2.setBounds(130,100,100,25);
        add(t2);
        
        l4=new Label("Enter marks s3");
        l4.setBounds(20,150,100,25);
        add(l4);
        
        t3=new TextField();
        t3.setBounds(130,150,100,25);
        add(t3);    
        
        b1=new Button("Result");
        b1.setBounds(80,200,40,20);
        add(b1);
        b1.addActionListener(this);
        
       l2=new Label("Total : ");
       l2.setBounds(20,240,100,25);
       add(l2);
       
       l5=new Label("Percentage : ");
       l5.setBounds(20,280,100,25);
       add(l5);
       
       l6=new Label("Grade : ");
       l6.setBounds(20,320,100,25);
       add(l6);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       int num1=Integer.parseInt(t1.getText());
       int num2=Integer.parseInt(t2.getText());
       int num3=Integer.parseInt(t3.getText());
       int Total=0;
       float per=0;
       String grade=null;
       if(e.getSource()==b1){
            Total=num1+num2+num3;
            l2.setText("Total = "+Total);
            per=(float)(Total/300.0f)*100.0f;
            l5.setText("Percentage ="+per);
            if(per>=75){
                grade="Distinction";
            }else if(per>60 && per<75){
                grade="First";
            }else{
                grade="Fail";
            }
            l6.setText("Grade ="+grade);
       }      
   }
    public static void main(String[] args) {
       AWTex5 a=new AWTex5();
    }
}

