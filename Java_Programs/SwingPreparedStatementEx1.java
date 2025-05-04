/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreparedStatement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author SAGAR
 */
public class SwingPreparedStatementEx1 extends JFrame{
    JLabel l1,l2,l3;
    JButton b1,b2,b3,b4;
    JTextField tf1;
    JPasswordField pf1;
    SwingPreparedStatementEx1(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        l1=new JLabel("username ");
        l1.setBounds(20,50,100,20);
        add(l1);
        
        tf1=new JTextField();
        tf1.setBounds(130,50,100,20);
        add(tf1);
        
        l2=new JLabel("password ");
        l2.setBounds(20,100,100,20);
        add(l2);
        
        pf1=new JPasswordField();
        pf1.setBounds(130,100,100,20);
        add(pf1);
        
        l3=new JLabel("status ");
        l3.setBounds(20,170,250,20);
        add(l3);
        
        b1=new JButton("save");
        b1.setBounds(20,130,80,25);
        add(b1);
        b1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    PreparedStatement pstmt=con.prepareStatement("insert into user(username,password) values(?,?)");
                    pstmt.setString(1,tf1.getText());
                    pstmt.setString(2,pf1.getText());
                    pstmt.executeUpdate();
                    con.close();
                    l3.setText("record Saved");
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }           
        });
        b2=new JButton("update");
        b2.setBounds(120,130,80,25);
        add(b2);
        b2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    PreparedStatement pstmt=con.prepareStatement("update user set password=? where username=?");
                    pstmt.setString(1,pf1.getText());
                    pstmt.setString(2,tf1.getText());
                    pstmt.executeUpdate();
                    con.close();
                    l3.setText("updated");
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }           
        });
        b3=new JButton("delete");
        b3.setBounds(220,130,80,25);
        add(b3);
        b3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    PreparedStatement pstmt=con.prepareStatement("delete from user where username=?");
                    pstmt.setString(1,tf1.getText());
                    pstmt.executeUpdate();
                    con.close();
                    l3.setText("Delete");
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }           
        });
        b4=new JButton("login");
        b4.setBounds(270,130,80,25);
        add(b4);
        b4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    PreparedStatement pstmt=con.prepareStatement("select * from user where username=? and password =?");
                    pstmt.setString(1,tf1.getText());
                    pstmt.setString(2,pf1.getText());
                    ResultSet rs=pstmt.executeQuery();
                    int temp=0;
                    while(rs.next()){
                        temp=1;
                    }
                    if(temp==1){
                        l3.setText("login sucess");
                    }else{
                        l3.setText("invalid");
                    }
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }
            });
    }
    public static void main(String[] args) {
        SwingPreparedStatementEx1 a=new SwingPreparedStatementEx1();
    }
}
