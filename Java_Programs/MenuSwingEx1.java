/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingExamples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
//Jlist
public class MenuSwingEx1 extends JFrame implements ActionListener{    
    JMenuBar mb;
    JMenu File,Edit,Help;
    JMenuItem cut,copy,paste,selectall;
    JTextArea ta;
    MenuSwingEx1(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        mb=new JMenuBar();
        File=new JMenu("File");
        
        Edit=new JMenu("Edit");
        cut=new JMenuItem("cut");
        cut.addActionListener(this);
        copy=new JMenuItem("copy");
        copy.addActionListener(this);
        paste=new JMenuItem("paste");
        paste.addActionListener(this);
        selectall=new JMenuItem("select all");
        selectall.addActionListener(this);
        
        Help=new JMenu("Help");
        
        ta=new JTextArea();    
        ta.setBounds(5,5,360,320);
        add(ta);
        
        add(mb);
        setJMenuBar(mb);
        mb.add(File);
        mb.add(Edit);
        mb.add(Help);
        Edit.add(cut);
        Edit.add(copy);
        Edit.add(paste);
        Edit.add(selectall);        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cut){
            ta.cut();
        }
        if(e.getSource()==copy){
            ta.copy();
        }
        if(e.getSource()==paste){
            ta.paste();
        }
        if(e.getSource()==selectall){
            ta.selectAll();
        }
    }
    public static void main(String[] args) {
        MenuSwingEx1 m=new MenuSwingEx1();
    }
}