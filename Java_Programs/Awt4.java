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
public class Awt4 extends Frame implements ActionListener{
    Checkbox c1,c2,c3,c4,c5,c6;
    CheckboxGroup g1,g2,g3;
    Label l1,l2,l3,l4;
    Button b1;
    Awt4(){
        setSize(400,700);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("capital of india ?");
        l1.setBounds(20,50,200,50);
        add(l1);

        g1=new CheckboxGroup();
        c1=new Checkbox("Mumbi",g1,true);
        c1.setBounds(50,100,200,50);
        add(c1);
        
        c2=new Checkbox("Delhi",g1,false);
        c2.setBounds(50,150,200,50);
        add(c2);
        
        l2=new Label("National Animal ?");
        l2.setBounds(20,200,200,50);
        add(l2);

        g2=new CheckboxGroup();
        c3=new Checkbox("Tiger",g2,true);
        c3.setBounds(50,250,200,50);
        add(c3);
        
        c4=new Checkbox("Lion",g2,false);
        c4.setBounds(50,300,200,50);
        add(c4);
        
        l3=new Label("National Bird ?");
        l3.setBounds(20,350,200,50);
        add(l3);

        g3=new CheckboxGroup();
        c5=new Checkbox("Peacock",g3,true);
        c5.setBounds(50,400,200,50);
        add(c5);
        
        c6=new Checkbox("Sparrow",g3,false);
        c6.setBounds(50,450,200,50);
        add(c6);
        
        b1=new Button("Press");
        b1.setBounds(100,500,200,20);
        add(b1);
        b1.addActionListener(this);
        
        l4=new Label("Result");
        l4.setBounds(10,550,200,50);
        add(l4);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int Total=0;
        if(c2.getState()==true){
            Total=Total+1;
        }
        if(c3.getState()==true){
            Total=Total+1;
        }
        if(c5.getState()==true){
            Total=Total+1;
        }
        l4.setText("Result ==="+Total);
    }
    public static void main(String[] args) {
        Awt4 a=new Awt4();
    }
}
