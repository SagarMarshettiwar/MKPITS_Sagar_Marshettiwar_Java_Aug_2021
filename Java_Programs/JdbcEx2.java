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
//CREATE TABLE
public class JdbcEx2 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            Statement stmt=con.createStatement();
                    String qr=("Create table Studentds(id int,name varchar(25),job varchar(25));");
                    stmt.execute(qr);
                    System.out.println("table created");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
