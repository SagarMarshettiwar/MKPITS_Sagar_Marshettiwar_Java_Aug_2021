/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingJdbcExamples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.DriverManager;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author SAGAR
 */
public class SjdbcEx2 extends JFrame{
    JLabel l1,l2,l3,l4,l5,l6;
    JTextField t1,t2,t3,t4,t5;
    JButton b1,b2,b3,b4;
    SjdbcEx2(){
        setSize(400,600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        l1=new JLabel("Rollno");
        l1.setBounds(30,50,80,40);
        add(l1);
        
        t1=new JTextField();
        t1.setBounds(100,50,80,40);
        add(t1);
        
        l2=new JLabel("Name");
        l2.setBounds(30,100,80,40);
        add(l2);
        
        t2=new JTextField();
        t2.setBounds(100,100,80,40);
        add(t2);
        
        l3=new JLabel("Address");
        l3.setBounds(30,150,80,40);
        add(l3);
        
        t3=new JTextField();
        t3.setBounds(100,150,80,40);
        add(t3);
        
        l4=new JLabel("email");
        l4.setBounds(30,200,80,40);
        add(l4);
        
        t4=new JTextField();
        t4.setBounds(100,200,80,40);
        add(t4);
        
        l5=new JLabel("Mobile no");
        l5.setBounds(30,250,80,40);
        add(l5);
        
        t5=new JTextField();
        t5.setBounds(100,250,80,40);
        add(t5);
        
        l6=new JLabel("Status");
        l6.setBounds(30,500,80,40);
        add(l6);
        
        b1=new JButton("insert");
        b1.setBounds(30,300,100,20);
        add(b1);
        b1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("Insert into stud(rollno,name,address,email,mobno) values ("+t1.getText()+",' "+t2.getText()+" ',' "+t3.getText()+" ',' "+t4.getText()+ " ',"+t5.getText()+ ")");
                    l6.setText("Record added");
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }
            
        });
        
        b2=new JButton("update");
        b2.setBounds(130,300,100,20);
        add(b2);
        b2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("update stud set name=' "+t2.getText()+" ',address=' "+t3.getText()+" ',email=' "+t4.getText()+ " ',mobno="+t5.getText()+" where rollno="+t1.getText()+" ");
                    l6.setText("Record updated");
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }
            
        });
        
        b3=new JButton("Delete");
        b3.setBounds(230,300,100,20);
        add(b3);
        b3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("delete from stud where rollno="+t1.getText()+" ");
                    l6.setText("Record Deleted");
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }
            
        });
        
        b3=new JButton("Search");
        b3.setBounds(30,350,100,20);
        add(b3);
        b3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("select * from stud where rollno="+t1.getText()+"");
                    int temp=0;
                    while(rs.next()){
                        temp=1;
                    }
                    if(temp==1){
                        l6.setText("Record found");
                    }else{
                        l6.setText("Record not found");
                    }
                }catch(Exception ee){
                    System.out.println(ee);
                }
            }
            
        });
    }
    public static void main(String[] args) {
        SjdbcEx2 s=new SjdbcEx2();
    }    
}
