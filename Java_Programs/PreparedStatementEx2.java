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
public class PreparedStatementEx2 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            PreparedStatement pstmt=con.prepareStatement("update user set password=? where username=? ");
            pstmt.setString(1,"parrot");
            pstmt.setString(2, "sagar");
            pstmt.executeUpdate();
            System.out.println("record updated");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
