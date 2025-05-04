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
public class JdbcEx15 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            Statement stmt=con.createStatement();
            stmt.executeUpdate("Delete from user where userid=2;");
            System.out.println("Delete............");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
