/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreparedStatement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class SwingPreparedStatementEx3 extends JFrame{
    JLabel l1,l2,l3,l4,l6;
    JTextField t1,t2,t3,t4;
    JButton b1,b2,b3,b4;
    SwingPreparedStatementEx3(){
        setSize(400,600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        l1=new JLabel("Product id");
        l1.setBounds(30,50,80,40);
        add(l1);
        
        t1=new JTextField();
        t1.setBounds(100,50,80,40);
        add(t1);
        
        l2=new JLabel("Product Name");
        l2.setBounds(30,100,80,40);
        add(l2);
        
        t2=new JTextField();
        t2.setBounds(100,100,80,40);
        add(t2);
        
        l3=new JLabel("Price");
        l3.setBounds(30,150,80,40);
        add(l3);
        
        t3=new JTextField();
        t3.setBounds(100,150,80,40);
        add(t3);
        
        l4=new JLabel("Quantity");
        l4.setBounds(30,200,80,40);
        add(l4);
        
        t4=new JTextField();
        t4.setBounds(100,200,80,40);
        add(t4);
        
        
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
                    PreparedStatement pstmt=con.prepareStatement("Insert into prod(prodid,prodname,price,quantity) values (?,?,?,?)");
                    pstmt.setInt(1, Integer.parseInt(t1.getText()));
                    pstmt.setString(2, t2.getText());
                    pstmt.setInt(3, Integer.parseInt(t3.getText()));
                    pstmt.setInt(4, Integer.parseInt(t4.getText()));
                    pstmt.executeUpdate();
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
                    PreparedStatement pstmt=con.prepareStatement("update prod set prodname=?,price=?,quantity=? where prodid=? ");
                    pstmt.setInt(4, Integer.parseInt(t1.getText()));
                    pstmt.setString(1, t2.getText());
                    pstmt.setInt(2, Integer.parseInt(t3.getText()));
                    pstmt.setInt(3, Integer.parseInt(t4.getText()));
                    pstmt.executeUpdate();                         
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
                    PreparedStatement pstmt=con.prepareStatement("delete from prod where prodid=?");
                    pstmt.setInt(1,Integer.parseInt(t1.getText()));
                    pstmt.executeUpdate();
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
                    PreparedStatement pstmt=con.prepareStatement("select * from prod where prodid=?");
                    pstmt.setInt(1,Integer.parseInt(t1.getText()));
                    ResultSet rs=pstmt.executeQuery();
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
        SwingPreparedStatementEx3 s=new SwingPreparedStatementEx3();
    }    
}

