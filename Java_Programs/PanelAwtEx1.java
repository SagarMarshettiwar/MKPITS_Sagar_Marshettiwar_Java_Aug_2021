/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.*;

/**
 *
 * @author SAGAR
 */
public class PanelAwtEx1 extends Frame{
    PanelAwtEx1(){
        Panel panel=new Panel();  
        panel.setBounds(40,80,200,200);    
        panel.setBackground(Color.gray);  
        Button b1=new Button("Button 1");     
        b1.setBounds(50,100,80,30);    
        b1.setBackground(Color.yellow);   
        Button b2=new Button("Button 2");   
        b2.setBounds(100,100,80,30);    
        b2.setBackground(Color.green);
        panel.add(b1);
        panel.add(b2);
        add(panel);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        PanelAwtEx1 p=new PanelAwtEx1();
    }
}
