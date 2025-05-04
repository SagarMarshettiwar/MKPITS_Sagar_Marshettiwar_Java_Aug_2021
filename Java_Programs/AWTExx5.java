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
public class AWTExx5 extends Frame{
    AWTExx5(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        setTitle("Login Example");
        Label l=new Label("MKPTS");
        l.setBounds(150,50,100,30);
        add(l);
        
        Label l1=new Label("SignIn Please");
        l1.setBounds(130,80,100,30);
        add(l1);
        
         Label l2=new Label("User Name =");
        l2.setBounds(50,130,100,30);
        add(l2);
        
       TextField t=new TextField();
        t.setBounds(140,130,200,25);
        add(t);
        
        Label l3=new Label("PassWord =");
        l3.setBounds(50,160,100,30);
        add(l3);
        
       TextField t1=new TextField();
        t1.setBounds(140,160,200,25);
        add(t1);
        
        Button b=new Button("LOGIN");
        b.setBounds(155, 200, 100, 30);
        add(b);
    }
    public static void main(String[] args) {
        AWTExx5 a=new AWTExx5();
    }
}
