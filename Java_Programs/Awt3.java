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
public class Awt3 extends Frame implements ActionListener{
    Checkbox r1,r2;
    CheckboxGroup g1;
    Label l1,l2;
    Button b1;
    Awt3(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Select Gender");
        l1.setBounds(10,50,100,20);
        add(l1);
        
        g1=new CheckboxGroup();
        r1=new Checkbox("male",g1,true);
        r1.setBounds(100,100,100,20);
        add(r1);
        
        r2=new Checkbox("Female",g1,false);
        r2.setBounds(100,130,100,20);
        add(r2);
        
        b1=new Button("Info");
        b1.setBounds(150,150,100,20);
        add(b1);
        b1.addActionListener(this);
        
        l2=new Label("info");
        l2.setBounds(10,200,200,50);
        add(l2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(r1.getState()==true){
            l2.setText("Gender is male");
        }else if(r2.getState()==true){
            l2.setText("Gender is Female");
        }
    }
    public static void main(String[] args) {
        Awt3 s=new Awt3();
    }
}
