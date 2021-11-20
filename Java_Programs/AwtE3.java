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
public class AwtE3 extends Frame{
    Label l1;
    TextField t1;
    AwtE3(){
        setSize(300,300);
        setLayout(null);
        setVisible(true);
        
        l1=new Label("Enter character");
        l1.setBounds(30,50,100,50);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(150,50,100,30);
        add(t1);
        t1.addKeyListener(new KeyAdapterClass(this));
    }
    public static void main(String[] args) {
        AwtE3 a=new AwtE3();
    }
}
class KeyAdapterClass extends KeyAdapter{
    AwtE3 awte3;

    public KeyAdapterClass(AwtE3 awte3) {
        this.awte3 = awte3;
    }
    public void keyTyped(KeyEvent ke){
        System.out.println("Key Typed "+ke.getKeyChar());
    }
}
