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
public class AWTExx6 extends Frame{
    AWTExx6(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        setTitle("ASSIGNMENT Example");
        Label l1=new Label("SignUP FORM");
        l1.setBounds(150,50,100,30);
        add(l1);
        
         Label l2=new Label("User Name =");
        l2.setBounds(50,110,100,30);
        add(l2);
        
       TextField t=new TextField();
        t.setBounds(140,110,200,25);
        add(t);
        
        Label l3=new Label("PassWord =");
        l3.setBounds(50,140,100,30);
        add(l3);
        
       TextField t1=new TextField();
        t1.setBounds(140,140,200,25);
        add(t1);
        
        Label l4=new Label("Email =");
        l4.setBounds(50,170,100,30);
        add(l4);
        
       TextField t2=new TextField();
        t2.setBounds(140,170,200,25);
        add(t2);
        
        Label l5=new Label("Mobile No =");
        l5.setBounds(50,200,100,30);
        add(l5);
        
       TextField t3=new TextField();
        t3.setBounds(140,200,200,25);
        add(t3);
        
        Button b=new Button("REGISTER");
        b.setBounds(155, 230, 100, 30);
        add(b);
    }
    public static void main(String[] args) {
        AWTExx6 a=new AWTExx6();
    }
}

