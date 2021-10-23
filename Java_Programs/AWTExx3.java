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
public class AWTExx3 extends Frame{
    public AWTExx3() {
        setSize(400,400);
        setVisible(true);
        setTitle("MKPITS");
        Button b=new Button("ok");
        b.setBounds(150, 100,100,50);
        setLayout(null);
        add(b);    
    }
    public static void main(String[] args) {
        AWTExx3 a=new AWTExx3();
    }
}