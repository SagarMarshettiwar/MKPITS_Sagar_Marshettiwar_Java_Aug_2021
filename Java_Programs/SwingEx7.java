/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class SwingEx7 extends JFrame implements ActionListener {
    JLabel l1,l2,l3,l4,l5,l6,l7,l8;
    JRadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9,rb10;
    ButtonGroup bg1,bg2,bg3,bg4,bg5;
    JButton b1;
    SwingEx7(){
        setSize(400,1000);
        setLayout(null);
        setVisible(true);
        l1=new JLabel("Quiz");
        l1.setBounds(150,50,100,50);
        add(l1);
        
        l2=new JLabel("Question 1");
        l2.setBounds(150,100,100,50);
        add(l2);
        
        rb1=new JRadioButton("Ans1");
        rb1.setBounds(150,150,100,50);
        add(rb1);
        
        rb2=new JRadioButton("Ans2");
        rb2.setBounds(150,200,100,50);
        add(rb2);
        
        l3=new JLabel("Question 2");
        l3.setBounds(150,250,100,50);
        add(l3);
        
        rb3=new JRadioButton("Ans1");
        rb3.setBounds(150,300,100,50);
        add(rb3);
        
        rb4=new JRadioButton("Ans2");
        rb4.setBounds(150,350,100,50);
        add(rb4);
        
        l4=new JLabel("Question 3");
        l4.setBounds(150,400,100,50);
        add(l4);
        
        rb5=new JRadioButton("Ans3");
        rb5.setBounds(150,450,100,50);
        add(rb5);
        
        rb6=new JRadioButton("Ans4");
        rb6.setBounds(150,500,100,50);
        add(rb6);
        
        l5=new JLabel("Question 4");
        l5.setBounds(150,550,100,50);
        add(l5);
        
        rb7=new JRadioButton("Ans5");
        rb7.setBounds(150,600,100,50);
        add(rb7);
        
        rb8=new JRadioButton("Ans6");
        rb8.setBounds(150,650,100,50);
        add(rb8);
        
        l6=new JLabel("Question 5");
        l6.setBounds(150,700,100,50);
        add(l6);
        
        rb9=new JRadioButton("Ans7");
        rb9.setBounds(150,750,100,50);
        add(rb9);
        
        rb10=new JRadioButton("Ans8");
        rb10.setBounds(150,800,100,50);
        add(rb10);
        
        b1=new JButton("Submit");
        b1.setBounds(150,840,100,50);
        add(b1);
        b1.addActionListener(this);
        
        l7=new JLabel("Correct : ");
        l7.setBounds(150,880,100,50);
        add(l7);
        
        l8=new JLabel("Wrong : ");
        l8.setBounds(300,880,100,50);
        add(l8);
        
        bg1=new ButtonGroup();
        bg1.add(rb1);
        bg1.add(rb2);
        
        bg2=new ButtonGroup();
        bg2.add(rb3);
        bg2.add(rb4);
        
        bg3=new ButtonGroup();
        bg3.add(rb5);
        bg3.add(rb6);
        
        bg4=new ButtonGroup();
        bg4.add(rb7);
        bg4.add(rb8);
        
        bg5=new ButtonGroup();
        bg5.add(rb9);
        bg5.add(rb10);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int total=0,loss=0;
        if(rb1.isSelected()==true){
            total+=1;
        }else if(rb2.isSelected()==true){
            loss+=1;
        }if(rb3.isSelected()==true){
            loss+=1;
        }else if(rb4.isSelected()==true){
             total+=1;
        }if(rb5.isSelected()==true){
             total+=1;
        }else if(rb6.isSelected()==true){
            loss+=1;
        }if(rb7.isSelected()==true){
            loss+=1;
        }else if(rb8.isSelected()==true){
            total+=1;
        }if(rb9.isSelected()==true){
            loss+=1;
        }else if(rb10.isSelected()==true){
            total+=1;
        }
        l7.setText("Correct : "+total);
        l8.setText("Wrong : "+loss);
        if(total>loss){
            JOptionPane.showMessageDialog(this,"You Pass :");
        }else{
            JOptionPane.showMessageDialog(this,"You Failed :");
        }
    }
    public static void main(String[] args) {
        SwingEx7 a=new SwingEx7();
    }
}
