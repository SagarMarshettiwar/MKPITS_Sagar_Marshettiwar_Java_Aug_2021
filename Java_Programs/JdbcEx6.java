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
public class JdbcEx6 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            Statement stmt=con.createStatement();
            String qr=("select * from Students;");
            ResultSet rs=stmt.executeQuery(qr);
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
