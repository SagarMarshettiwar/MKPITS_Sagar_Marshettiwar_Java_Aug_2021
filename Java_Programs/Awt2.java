/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples3;

import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.*;
/**
 *
 * @author SAGAR
 */
public class Awt2 extends Frame implements ItemListener{    
    Label l1;
    Checkbox c1,c2;
    Awt2(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        c1=new Checkbox("C++");
        c1.setBounds(50,100,50,50);
        add(c1);
        c1.addItemListener(this);
        
        c2=new Checkbox("java");
        c2.setBounds(100,100,50,50);
        add(c2);
        c2.addItemListener(this);
        
        l1=new Label("HI");
        l1.setAlignment(l1.CENTER);
        add(l1);
        l1.setSize(400,200);
    }     
       public void itemStateChanged(ItemEvent e) {
           l1.setText("");
           if(c1.getState()==true){
              l1.setText("C++ Clicked");
           }else if(c2.getState()==true){
               l1.setText("Java Clicked");
           }
       }   
    public static void main(String[] args) {
        Awt2 a=new Awt2();
    }
}
   