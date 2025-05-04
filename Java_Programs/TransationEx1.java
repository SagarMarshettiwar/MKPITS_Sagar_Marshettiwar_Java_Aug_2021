/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransactionPackage;
import java.sql.*;
/**
 *
 * @author SAGAR
 */
public class TransationEx1 {
    public static void main(String[] arg) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            // System.out.println("successfully connected");
            con.setAutoCommit(false);
            Statement stmt=con.createStatement();
            stmt.executeUpdate("insert into user(username,password) values('abhi','abc')");
            stmt.executeUpdate("insert into user(username,password) values('umesh','efg')");
            con.commit();
            System.out.println("insert");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
