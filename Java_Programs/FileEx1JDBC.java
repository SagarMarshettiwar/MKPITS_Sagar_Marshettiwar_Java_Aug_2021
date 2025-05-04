/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileinJDBC;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author SAGAR
 */
public class FileEx1JDBC {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            File f=new File("E:\\hell.txt");
            FileInputStream fis=new FileInputStream(f);
            PreparedStatement pstmt=con.prepareStatement("insert into imageex(name,image) values (?,?)");
            pstmt.setString(1,"sagar");
            pstmt.setAsciiStream(2, fis,(int)f.length());
            pstmt.executeUpdate();
            System.out.println("File Saved");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
