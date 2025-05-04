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
public class Awt6 extends Frame implements ActionListener{
    Label l1,l2,l3,l4;
    TextField t1;
    TextArea ta;
    Checkbox c1,c2,c3,c4;
    CheckboxGroup g1;
    Button b1;
    Awt6(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("UserName");
        l1.setBounds(20,80,70,20);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(90,80,90,20);
        add(t1);
        
        l2=new Label("Gender");
        l2.setBounds(20,120,50,20);
        add(l2);
        
        g1=new CheckboxGroup();
        c1=new Checkbox("Male",g1,true);
        c1.setBounds(80,120,50,20);
        add(c1);
        
        c2=new Checkbox("Female",g1,false);
        c2.setBounds(150,120,70,20);
        add(c2);
        
        l3=new Label("Hobby");
        l3.setBounds(20,160,50,20);
        add(l3);
        
        c3=new Checkbox("music");
        c3.setBounds(80,160,50,20);
        add(c3);
        
        c4=new Checkbox("Cricket");
        c4.setBounds(150,160,70,20);
        add(c4);
        
        b1=new Button("ok");
        b1.setBounds(140,190,50,20);
        add(b1);
        b1.addActionListener(this);
        
        ta=new TextArea();
        ta.setBounds(20,220,200,150);
        add(ta);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name=t1.getText();
        String gender =" ";
        String hobby = " ";
        StringBuilder sb=new StringBuilder();
        if(e.getSource()==b1){
            if(c1.getState()==true){
                gender="male";
            }else if(c2.getState()==true) {
                gender="female";
            }
            if(c3.getState()==true){
                hobby="music";
            }else if(c4.getState()==true){
                hobby="Cricket";
            }
        }     
        sb.append("Username= "+name+"\n"+"Gender= "+gender+"\n"+"Hobby= "+hobby);
        ta.setText(sb.toString());
    }
        
    public static void main(String[] args) {
        Awt6 a=new Awt6();
    }
}
