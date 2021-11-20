/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples3;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author SAGAR
 */
public class Awt5 extends Frame implements ActionListener{
    TextField t1,t2;
    CheckboxGroup g1;
    Checkbox c1,c2;
    Button b1;
    Label l1,l2,l3;
    Awt5(){
        setSize(400,500);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Account No");
        l1.setBounds(20,50,70,20);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(100,50,60,20);
        add(t1);
        
        l2=new Label("Amount");
        l2.setBounds(20,100,50,20);
        add(l2);
        
        t2=new TextField();
        t2.setBounds(100,100,60,20);
        add(t2);
        
        g1=new CheckboxGroup();
        c1=new Checkbox("Deposit",g1,true);
        c1.setBounds(30,150,80,20);
        add(c1);
        
        c2=new Checkbox("Withdrawl",g1,false);
        c2.setBounds(120,150,80,20);
        add(c2);

        b1=new Button("OK");
        b1.setBounds(150,200,80,20);
        add(b1);
        b1.addActionListener(this);
        
        l3=new Label("Balance is");
        l3.setBounds(30,210,300,200);
        add(l3);
    }
    int balance=0;
    @Override
    public void actionPerformed(ActionEvent e) {
        int accountNO=Integer.parseInt(t1.getText());
        int amount=Integer.parseInt(t2.getText());
     
        if(c1.getState()==true){
            balance=balance+amount;            
        }
        if(c2.getState()==true){
            balance=balance-amount;
        }    
        l3.setText("Account no ="+accountNO+"  balance is ="+balance);
    }
    public static void main(String[] args) {
        Awt5 a=new Awt5();
    }
}
