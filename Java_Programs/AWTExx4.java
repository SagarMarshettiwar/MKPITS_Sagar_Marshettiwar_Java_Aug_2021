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
public class AWTExx4 {

    public AWTExx4() {
        Frame f=new Frame();
        f.setSize(400,400);
        f.setVisible(true);
        f.setTitle("MKPITS");
        Button b=new Button("OK");
        b.setBounds(50, 50, 200, 100);
        f.add(b);
        f.setLayout(null);
        
    }
    public static void main(String[] args) {
        AWTExx4 a=new AWTExx4();
    }
}
