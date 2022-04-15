/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author SAGAR
 */
public class BankMenu extends JFrame implements MenuListener{
//    JLabel l1,l2,l3;
//    JTextField tf1;
//    JPasswordField pf1;
//    JButton b1;
   JMenuBar menubar;
   JMenu arec,drec,urec,vrec,varec,darec;
   DisplayAllRecord dar;
   DisplayRecord dr;
   StringBuffer sb;
//   JMenuItem mi1;
   public BankMenu() {
        sb=new StringBuffer();
       
        menubar = new JMenuBar();
        arec=new JMenu("Add Rec");
        arec.addMenuListener(this);
        drec=new JMenu("Delete Rec");
        drec.addMenuListener(this);
        urec=new JMenu("Update Rec");
        vrec=new JMenu("View Rec");
        vrec.addMenuListener(this);
        varec=new JMenu("ViewAll Rec");
        varec.addMenuListener(this);
        darec=new JMenu("DeleteAll Rec");
        
//        mi1=new JMenuItem("abc");
                
        menubar.add(arec);
        menubar.add(drec);
        menubar.add(urec);
        menubar.add(vrec);
        menubar.add(varec);
        menubar.add(darec);
        
//        arec.add(mi1);
        
        setSize(500,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        setJMenuBar(menubar);
         
    }
    public static void main(String[] args) {
        BankMenu b=new BankMenu();
    }
    
    @Override
    public void menuSelected(MenuEvent e) {
        
        if(e.getSource()==arec){
            new AddRecord();
//            dispose();
            
        }
        if(e.getSource()==varec){
                dar=new DisplayAllRecord();
                for(Customer ob: AddRecord.alist){
                    dar.ta.setText(new String(sb.append(ob)));
                }
        } 
         if(e.getSource()==vrec){
            String inputValue = JOptionPane.showInputDialog("Please input a value to Select");
            int in=Integer.parseInt(inputValue);
            System.out.println(in);
            for(Customer ob: AddRecord.alist){                  
                    if(ob.id==in){
                        dr=new DisplayRecord();
                        dr.ta.setText(new String(ob.toString()));
                        System.out.println("found");
                    }
            }
            //new DisplayRecord();  
        } 
         if(e.getSource()==drec){
            String inputValue1 = JOptionPane.showInputDialog("Please input a value to delete");
            int in1=Integer.parseInt(inputValue1);
            System.out.println(in1);
            for(Customer ob: AddRecord.alist){                  
                    if(ob.id==in1){
                        dar.ta.setText(ob.remove(ob.id));
                        System.out.println("delete"+ob.id);
                    }
        }
    }
    }        
    @Override
    
    public void menuDeselected(MenuEvent e) {
       
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        
    }
}    