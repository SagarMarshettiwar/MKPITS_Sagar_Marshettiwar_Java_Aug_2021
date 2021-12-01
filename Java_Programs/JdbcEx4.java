/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MkpitsJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author SAGAR
 */
//update
public class JdbcEx4 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            Statement stmt=con.createStatement();
            String qr=("update Students set name='motu' where id=4;");
            stmt.executeUpdate(qr);
            System.out.println("Updated............");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
