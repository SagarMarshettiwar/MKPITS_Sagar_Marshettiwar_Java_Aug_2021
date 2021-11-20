/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author SAGAR
 */
public class PopupMenuEx1Type2{
    PopupMenu pm1;
    MenuItem mi1,mi2,mi3;
    Frame f;
    PopupMenuEx1Type2(){
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
       f.addMouseListener(new MouseAdapter() {
       public void mouseClicked(MouseEvent e) {
            pm1.show(f , e.getX(), e.getY());
        }
        });
     }            
    public static void main(String[] args) {
        PopupMenuEx1Type2 p=new PopupMenuEx1Type2();
    }
}

