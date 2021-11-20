/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 *
 * @author SAGAR
 */
public class ScrollBarAwtEx2 extends Frame{
    ScrollBarAwtEx2(){
        Label label = new Label();
        label.setAlignment(Label.CENTER);
        label.setSize(400,100);
        add(label);
        Scrollbar s=new Scrollbar();
        s.setBackground(Color.red);
        
        s.setBounds(100,100, 50,100);
        add(s);
        s.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                 label.setText("Vertical Scrollbar value is:"+ s.getValue());
            }
        });
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        ScrollBarAwtEx2 s=new ScrollBarAwtEx2();
    }
}
