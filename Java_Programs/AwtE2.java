/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples4;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author SAGAR
 */

public class AwtE2 extends Frame implements KeyListener{
    Label l1;
    TextField t1;
    AwtE2(){
        setSize(300,300);
        setLayout(null);
        setVisible(true);
        
        l1=new Label("Enter character");
        l1.setBounds(30,50,100,50);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(150,50,100,30);
        add(t1);
        t1.addKeyListener(this);
    }
    public static void main(String[] args) {
        AwtE2 a=new AwtE2();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("key are "+e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed "+e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("key "+e.getKeyChar());
    }
}
