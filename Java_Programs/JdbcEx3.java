/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MkpitsJDBC;

import java.sql.*;

/**
 *
 * @author SAGAR
 */
//Insert
public class JdbcEx3 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            Statement stmt=con.createStatement();
            String qr=("insert into Students(id,name,job)values(2,'dolly','java'),(3,'Ameya','sql'),(4,'chotu','pogo');");
            stmt.executeUpdate(qr);
            System.out.println("Inserted........");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}