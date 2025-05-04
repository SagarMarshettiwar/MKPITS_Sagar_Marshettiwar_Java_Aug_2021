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
public class AwtE5 extends Frame{
    TextField text1;
    public AwtE5(){
        text1=new TextField();
        text1.setBounds(80,200,240,30);
        add(text1);
        addMouseListener(new MouseEx(this));
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        AwtE5 a=new AwtE5();
    }
}
class MouseEx extends MouseAdapter{
   AwtE5 awte5;

    public MouseEx(AwtE5 awte5) {
        this.awte5 = awte5;
    }
    public void mousePressed(MouseEvent e){
        awte5.text1.setText("left mouse button down at "
        + e.getX() + "," + e.getY());
    }
}