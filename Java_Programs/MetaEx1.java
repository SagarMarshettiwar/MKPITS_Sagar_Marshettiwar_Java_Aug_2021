/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResultSetMetaEx1;
import java.sql.*;
/**
 *
 * @author SAGAR
 */
public class MetaEx1 {
    public static void main(String args[]) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
        Statement stmt = con.createStatement();
        //Retrieving the data
        ResultSet rs = stmt.executeQuery("select * from user");
        ResultSetMetaData rsMetaData = rs.getMetaData();
        //Number of columns
        System.out.println("Number of columns:"+rsMetaData.getColumnCount());
        //Column label
        System.out.println("Column Label: "+rsMetaData.getColumnLabel(1));
        //Column name
        System.out.println("Column Name: "+rsMetaData.getColumnName(2));
        //Number of columns
        System.out.println("Table Name: "+rsMetaData.getTableName(1));
    }
}
