/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author SAGAR
 */
 public class AddRecord extends JFrame{
    JLabel l1,l2,l3,l4,l5;
    JTextField tf1,tf2,tf3,tf4,tf5;
    JButton b1;
     static ArrayList <Customer> alist;
    public AddRecord() {
        alist =new ArrayList<Customer>();
        setSize(500,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        l1=new JLabel(" Unique ID");
        l1.setBounds(20,50,80,20);
        add(l1);
        
        tf1=new JTextField();
        tf1.setBounds(100,50,80,20);
        add(tf1);
        
        l2=new JLabel("First Name");
        l2.setBounds(20,100,80,20);
        add(l2);
        
        tf2=new JTextField();
        tf2.setBounds(100,100,80,20);
        add(tf2);
        
        l3=new JLabel("Email");
        l3.setBounds(20,150,80,20);
        add(l3);
        
        tf3=new JTextField();
        tf3.setBounds(100,150,80,20);
        add(tf3);
        
        l4=new JLabel("Phone no");
        l4.setBounds(20,200,80,20);
        add(l4);
        
        tf4=new JTextField();
        tf4.setBounds(100,200,80,20);
        add(tf4);
        
        l5=new JLabel("Account Type");
        l5.setBounds(20,250,80,20);
        add(l5);
        
        tf5=new JTextField();
        tf5.setBounds(100,250,80,20);
        add(tf5);
        
        b1=new JButton("Add Record");
        b1.setBounds(150,300,80,20);
        add(b1);
        b1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer c=new Customer();
                c.setId(Integer.parseInt(tf1.getText()));
                c.setName(String.valueOf(tf2.getText()));
                c.setEmail(String.valueOf(tf3.getText()));
                c.setPhoneno(String.valueOf(tf4.getText()));
                c.setAcctyp(String.valueOf(tf5.getText()));
                
                if(e.getSource()==b1){                 
                    alist.add(c);
                    
//                    for(Customer ob: alist){
//                        System.out.println(ob);
//                    }
//                    System.out.println(alist);
                }
                //System.out.println(alist.size());
            }    
        });     
    }

    public static void main(String[] args) {
        AddRecord a=new AddRecord();
    }
}
