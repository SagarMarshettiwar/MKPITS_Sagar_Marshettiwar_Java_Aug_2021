/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.Frame;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;

/**
 *
 * @author SAGAR
 */
public class PopupMenuEx1{
    PopupMenu pm1;
    MenuItem mi1,mi2,mi3;
    Frame f;
    PopupMenuEx1(){
        f=new Frame();
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
        
       pm1=new PopupMenu("Edit");
       mi1=new MenuItem("cut");
       mi1.setActionCommand("cut");
       mi2=new MenuItem("copy");
       mi2.setActionCommand("copy");
       mi3=new MenuItem("paste");
       mi3.setActionCommand("paste");
       pm1.add(mi1);
       pm1.add(mi2);
       pm1.add(mi3);
       f.add(pm1);
       f.addMouseListener(new Pop(this,f));
     }            
    public static void main(String[] args) {
        PopupMenuEx1 p=new PopupMenuEx1();
    }
}
class Pop extends MouseAdapter{
    PopupMenuEx1 popup;
    Frame f;
    public Pop(PopupMenuEx1 popup,Frame f) {
        this.popup = popup;
        this.f=f;
    }
    public void mouseClicked(MouseEvent e) {
       popup.pm1.show(f, e.getX(),e.getY());
    }
}