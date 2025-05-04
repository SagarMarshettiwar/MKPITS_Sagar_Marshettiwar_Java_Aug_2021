/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class ListAwtEx2 extends Frame implements ActionListener{
    List l,l1;
    Button b;
    Label la1;
    ListAwtEx2(){
        setSize(400,500);
        setLayout(null);
        setVisible(true);
        
        l=new List(5);
        l.setBounds(100,100,75,75);
        l.add("item 1");
        l.add("item 2");
        l.add("item 3");
        l.add("item 4");
        l.add("item 5");
        add(l);
        
        l1=new List(5);
        l1.setBounds(100,200,75,75);
        l1.add("item 1");
        l1.add("item 2");
        l1.add("item 3");
        l1.add("item 4");
        l1.add("item 5");
        add(l1);
        
        la1=new Label("items");
        la1.setAlignment(Label.CENTER);
        la1.setSize(500,400);
        add(la1);
        
        b=new Button("Show");
        b.setBounds(200,400,80,30);
        add(b);
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        String c="Programming Language : "+l.getItem(l.getSelectedIndex());
        String f="Frame Work :"+l1.getItem(l1.getSelectedIndex());
        StringBuilder sb=new StringBuilder();
        sb.append(c+" ");
        sb.append(f);
        la1.setText(sb.toString());
    }
    public static void main(String[] args) {
        ListAwtEx2 l=new ListAwtEx2();
    }
}
