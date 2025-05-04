/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class SwingEx5 extends JFrame implements ActionListener{
    JTextField t;
    Font f;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,b11,b12,b13,b14,b15,b16;
    String operand1,operand2,operator;
    SwingEx5(){
        operand1=operand2=operator=" ";
        setSize(400,400);
        setVisible(true);
        setLayout(null);       
        setTitle("Calculater Assignment");
        
        f=new Font("SansSerif",0,30);
        
        t=new JTextField();
        t.setFont(f);
        t.setBounds(30, 50, 270, 40);
        add(t);
        
        b1=new JButton("1");
        b1.setBounds(30, 100, 90, 40);
        add(b1);
        b1.addActionListener(this);
        
        b2=new JButton("2");
        b2.setBounds(160, 100, 90, 40);
        add(b2);
        b2.addActionListener(this);
        
        b3=new JButton("3");
        b3.setBounds(280, 100, 90, 40);
        add(b3);
        b3.addActionListener(this);
        
        b4=new JButton("4");
        b4.setBounds(30, 150, 90, 40);
        add(b4);
        b4.addActionListener(this);
        
        b5=new JButton("5");
        b5.setBounds(160, 150, 90, 40);
        add(b5);
        b5.addActionListener(this);
        
        b6=new JButton("6");
        b6.setBounds(280, 150, 90, 40);
        add(b6);
        b6.addActionListener(this);
        
        b7=new JButton("7");
        b7.setBounds(30, 200, 90, 40);
        add(b7);
        b7.addActionListener(this);
        
        b8=new JButton("8");
        b8.setBounds(160, 200, 90, 40);
        add(b8);
        b8.addActionListener(this);
        
        b9=new JButton("9");
        b9.setBounds(280, 200, 90, 40);
        add(b9);
        b9.addActionListener(this);
        
        b0=new JButton("0");
        b0.setBounds(160, 250, 90, 40);
        add(b0);
        b0.addActionListener(this);
        
        b11=new JButton("+");
        b11.setBounds(30, 250, 90, 40);
        add(b11);
        b11.addActionListener(this);
        
        b12=new JButton("-");
        b12.setBounds(30, 300, 90, 40);
        add(b12);
        b12.addActionListener(this);
        
        b13=new JButton("=");
        b13.setBounds(280, 250, 90, 40);
        add(b13);
        b13.addActionListener(this);
        
        b14=new JButton("C");
        b14.setBounds(310, 50, 60, 40);
        add(b14);
        b14.addActionListener(this);
        
        b15=new JButton("*");
        b15.setBounds(280, 300, 90, 40);
        add(b15);
        b15.addActionListener(this);
        
        b16=new JButton("/");
        b16.setBounds(160, 300, 90, 40);
        add(b16);
        b16.addActionListener(this);
     }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
      if (command.charAt(0) == 'C') {                      
         t.setText("");
      }else if (command.charAt(0) == '=') {
         t.setText(process(t.getText()));//it will give overall result
      }else {                                
         t.setText(t.getText() + command);//it will display the button keys on textfield
      }
   }
    public String process(String expression) {
      char[] arr = expression.toCharArray();
      operand1 = "";operand2 = "";operator = "";
      double result = 0;
      for (int i=0;i<arr.length;i++){
           if(arr[i] == '+' || arr[i] == '-' || arr[i] == '/' || arr[i] == '*') {
            operator += arr[i];
         }
          if (arr[i] >= '0'  || arr[i] == '.') {
              if(operator.isEmpty()){
                  operand1+=arr[i];
              }else{
                  operand2+=arr[i];
              }
          }         
      }
      if (operator.equals("+")){
         result = (Double.parseDouble(operand1) + Double.parseDouble(operand2));
      }else if (operator.equals("-")){
         result = (Double.parseDouble(operand1) - Double.parseDouble(operand2));
      }else if (operator.equals("/")){
         result = (Double.parseDouble(operand1) / Double.parseDouble(operand2));
      }else{
         result = (Double.parseDouble(operand1) * Double.parseDouble(operand2));          
      }
      return operand1 + operator + operand2 + "=" +result;
    }
      public static void main(String[] args) {
        SwingEx5 a=new SwingEx5();
    }
}