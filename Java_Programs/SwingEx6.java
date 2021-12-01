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
public class SwingEx6 extends JFrame implements ActionListener {
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
    JTextField tf;
    JButton b1;
    JComboBox cob1;
    JRadioButton rb1,rb2;
    JCheckBox cb1,cb2;
    SwingEx6(){
        setSize(400,600);
        setLayout(null);
        setVisible(true);
        l1=new JLabel("Registration Form");
        l1.setBounds(150,50,200,30);
        add(l1);
        
        l2=new JLabel("Username");
        l2.setBounds(50,100,100,30);
        add(l2);
        
        tf=new JTextField();
        tf.setBounds(150,100,100,30);
        add(tf);
        
        l3=new JLabel("Hobby");
        l3.setBounds(50,150,100,30);
        add(l3);
        
        cb1=new JCheckBox("music");
        cb1.setBounds(150,150,100,50);
        add(cb1);
        
        cb2=new JCheckBox("Dance");
        cb2.setBounds(250,150,100,50);
        add(cb2);
        
        l4=new JLabel("gender");
        l4.setBounds(50,200,100,30);
        add(l4);
        
        rb1=new JRadioButton("male");
        rb1.setBounds(150,200,100,30);
        add(rb1);
        
        rb2=new JRadioButton("female");
        rb2.setBounds(250,200,100,30);
        add(rb2);
        
        ButtonGroup bg1=new ButtonGroup();
        bg1.add(rb1);
        bg1.add(rb2);
        
        l5=new JLabel("City");
        l5.setBounds(50,250,100,50);
        add(l5);
        
        String C[]={"nagpur","mumbai","delhi","amritsar" };
        cob1=new JComboBox(C);
        cob1.setBounds(150,250,150,50);
        add(cob1);
        
        b1=new JButton("Register");
        b1.setBounds(150,450,100,25);
        b1.addActionListener(this);
        add(b1);
        b1.addActionListener(this);
        
        l6=new JLabel("Username : ");
        l6.setBounds(50,475,400,30);
        add(l6);

        l7=new JLabel("Hobby : ");
        l7.setBounds(50,500,400,30);
        add(l7);

        l8=new JLabel("Gender : ");
        l8.setBounds(50,525,400,30);
        add(l8);

        l9=new JLabel("City : ");
        l9.setBounds(50,550,400,30);
        add(l9);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        l6.setText("UserName: "+tf.getText());
        StringBuilder sb=new StringBuilder();
        sb.append("");
        if(cb1.isSelected()==true){
            sb.append("Hobby :music");
        }else if(cb2.isSelected()==true){
            sb.append("Hobby :Dance");
        }
        l7.setText(sb.toString());
        
        if(rb1.isSelected()==true){
            l8.setText("Gender :Male");
        }else if(rb2.isSelected()==true){
            l8.setText("Gender :Female");
        }
        l9.setText("City : "+cob1.getItemAt(cob1.getSelectedIndex()));
        StringBuilder sb1=new StringBuilder();
        sb1.append("UserName: "+tf.getText()+"\n");
        sb1.append(sb.toString()+"\n");
        sb1.append(l8.getText()+"\n");
        sb1.append(l9.getText()+"\n");
        JOptionPane.showMessageDialog(this, sb1.toString());
    }
    public static void main(String[] args) {
        SwingEx6 s=new SwingEx6();
    }
}