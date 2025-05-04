/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageinJDBC;
import java.io.*;
import java.sql.*;
/**
 *
 * @author SAGAR
 */
public class ImagEx1 {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
            File f=new File("E:\\mona.jpg");
            FileInputStream fis=new FileInputStream(f);
            PreparedStatement pstmt=con.prepareStatement("insert into imageex(name,image) values (?,?)");
            pstmt.setString(1,"sagar");
            pstmt.setBinaryStream(2, fis,(int)f.length());
            pstmt.executeUpdate();
            System.out.println("Image Saved");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
