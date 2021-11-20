/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.Frame;
import java.awt.Scrollbar;

/**
 *
 * @author SAGAR
 */
public class ScrollBarAwtEx1 extends Frame{
    ScrollBarAwtEx1(){
        Scrollbar s=new Scrollbar(Scrollbar.HORIZONTAL);
        s.setBounds(100,100, 300,100);
        add(s);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        ScrollBarAwtEx1 a=new ScrollBarAwtEx1();
    }
}
