/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransactionPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author SAGAR
 */
public class BatchProcessEx1 {
    public static void main(String[] arg) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            // System.out.println("successfully connected");
            con.setAutoCommit(false);
            Statement stmt=con.createStatement();
            stmt.addBatch("insert into user(username,password) values('lol','abc')");
            stmt.addBatch("insert into user(username,password) values('call','efg')");
            stmt.executeBatch();
            con.commit();
            System.out.println("insert");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
