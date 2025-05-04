/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples;
import java.awt.*;
/**
 *
 * @author SAGAR
 */
public class AWTExx7 extends Frame {
    AWTExx7(){
        setSize(400,370);
        setVisible(true);
        setLayout(null);
        setTitle("Calculater Assignment");
        
        Font f=new Font("SansSerif",0,30);
        
        TextField t=new TextField();
        t.setFont(f);
        t.setBounds(30, 50, 270, 40);
        add(t);
        
        Button b1=new Button("1");
        b1.setBounds(30, 100, 90, 40);
        add(b1);
        
        Button b2=new Button("2");
        b2.setBounds(160, 100, 90, 40);
        add(b2);
        
        Button b3=new Button("3");
        b3.setBounds(280, 100, 90, 40);
        add(b3);
        
        Button b4=new Button("4");
        b4.setBounds(30, 150, 90, 40);
        add(b4);
        
        Button b5=new Button("5");
        b5.setBounds(160, 150, 90, 40);
        add(b5);
        
        Button b6=new Button("6");
        b6.setBounds(280, 150, 90, 40);
        add(b6);
        
        Button b7=new Button("7");
        b7.setBounds(30, 200, 90, 40);
        add(b7);
        
        Button b8=new Button("8");
        b8.setBounds(160, 200, 90, 40);
        add(b8);
        
        Button b9=new Button("9");
        b9.setBounds(280, 200, 90, 40);
        add(b9);
        
        Button b0=new Button("0");
        b0.setBounds(160, 250, 90, 40);
        add(b0);
        
        Button b11=new Button("+");
        b11.setBounds(30, 250, 90, 40);
        add(b11);
        
        Button b12=new Button("-");
        b12.setBounds(30, 300, 90, 40);
        add(b12);
        
        Button b13=new Button("=");
        b13.setBounds(280, 250, 90, 40);
        add(b13);
        
        Button b14=new Button("C");
        b14.setBounds(310, 50, 60, 40);
        add(b14);
        
        Button b15=new Button("*");
        b15.setBounds(280, 300, 90, 40);
        add(b15);
        
        Button b16=new Button("/");
        b16.setBounds(160, 300, 90, 40);
        add(b16);
     }
    public static void main(String[] args) {
        AWTExx7 a=new AWTExx7();
    }
}
