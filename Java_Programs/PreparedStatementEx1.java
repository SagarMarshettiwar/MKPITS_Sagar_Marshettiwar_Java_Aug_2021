/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreparedStatement;

import java.sql.*;

/**
 *
 * @author SAGAR
 */
public class PreparedStatementEx1 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            PreparedStatement pstmt=con.prepareStatement("insert into user (username,password) values (?,?)");
            pstmt.setString(1,"pappu");
            pstmt.setString(2, "booa");
            pstmt.executeUpdate();
            System.out.println("record added");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
