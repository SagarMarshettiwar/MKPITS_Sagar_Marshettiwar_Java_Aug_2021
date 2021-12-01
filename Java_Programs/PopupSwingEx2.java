/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author SAGAR
 */
public class PopupSwingEx2 implements ActionListener{
    JPopupMenu p1;
    JMenuItem cut,copy,paste;
    JLabel label;
    PopupSwingEx2(){
        JFrame f=new JFrame();
        label = new JLabel("hello");          
        label.setHorizontalAlignment(JLabel.CENTER);  
        label.setSize(400,100);
        p1=new JPopupMenu();
        cut=new JMenuItem("cut");
        cut.addActionListener(this);
        copy=new JMenuItem("copy");
        copy.addActionListener(this);
        paste=new JMenuItem("paste");
        paste.addActionListener(this);
        f.add(p1);
        p1.add(cut);
        p1.add(copy);
        p1.add(paste);
        f.add(label);
        f.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e){
               p1.show(f, e.getX(),e.getY());
           } 
        });
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);   
    }
    public static void main(String[] args) {
         PopupSwingEx2 p=new PopupSwingEx2();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cut){
            label.setText("Cut is clicked");
        }if(e.getSource()==copy){
            label.setText("Copy is clicked");
        }if(e.getSource()==paste){
            label.setText("paste is clicked");
        }
    }
}    