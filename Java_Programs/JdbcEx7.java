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
public class JdbcEx7 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            Statement stmt=con.createStatement();
            stmt.execute("create table employees(empno int AUTO_INCREMENT,empname varchar(25),address varchar(30),PRIMARY KEY(empno));");
            System.out.println("table created");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
