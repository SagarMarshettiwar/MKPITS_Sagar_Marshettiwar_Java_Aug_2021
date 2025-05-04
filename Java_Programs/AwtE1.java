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
public class AwtE1 extends Frame {
    AwtE1(){
        addMouseListener(new MyMouseAdapter(this));
        addMouseMotionListener(new MyMouseMotionAdapter(this));
        setSize(300,300);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        AwtE1 a=new AwtE1();
    }
}
class MyMouseAdapter extends MouseAdapter{
    AwtE1 awte1;

    public MyMouseAdapter(AwtE1 awte1) {
        this.awte1 = awte1;
    }
    public void mouseClicked(MouseEvent me){
        System.out.println("mouse Clicked"+me.getX()+" , "+me.getY());
    }
}
class MyMouseMotionAdapter extends MouseMotionAdapter{
    AwtE1 awte1;

    public MyMouseMotionAdapter(AwtE1 awte1) {
        this.awte1 = awte1;
    }
    public void mouseDragged(MouseEvent me){
        System.out.println("mouse Dragged");
    }
}