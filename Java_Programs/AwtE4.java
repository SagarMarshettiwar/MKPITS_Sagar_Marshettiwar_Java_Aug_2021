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
public class AwtE4 extends Frame{
    Label l1;
    TextField t1;
    AwtE4(){
        l1=new Label("mkpits");
        l1.setBounds(10,50,250,20);
        add(l1);
    
        t1=new TextField();
        t1.setBounds(10,120,250,20);
        t1.addKeyListener(new keyadapterclass(this));

        add(t1);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] arg) {
        AwtE4 a=new AwtE4();
    }
}
class keyadapterclass extends KeyAdapter{
    AwtE4 awte4;
    keyadapterclass(AwtE4 awte4){
        this.awte4=awte4;
    }
    public void keyTyped(KeyEvent ke) {
        System.out.println("key typed " );
        char c = ke.getKeyChar();
        System.out.println("char typed " + c);
        awte4.l1.setText(awte4.t1.getText());
    }
}
