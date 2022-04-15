/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SAGAR
 */
public class DisplayAllRecord extends JFrame{
   JTextArea ta;
//    JTable table;
//    JButton b1;
    public DisplayAllRecord() {
        setSize(700,700);
        setLayout(null);
        setVisible(true);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
         
//add(table);   
        ta=new JTextArea(2,3);
        ta.setBounds(30,30,600,600);
        add(ta);
//           String data [][]={{" "," "," "}}; 
//          String column[]={"ID","Name","Email"};  
//          table =new JTable(data,column);
//          table.setBounds(30,30,400,500);
//          JScrollPane sp=new JScrollPane(table);    
//           add(sp); 
//          
//          b1 =new JButton("press");
//          b1.setBounds(150,500,80,40);
//          add(b1);
//          b1.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
//            }
//              
//          });
    }
    public static void main(String[] args) {
        DisplayAllRecord a=new DisplayAllRecord();
    }
}
