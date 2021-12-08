/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageinJDBC;
import java.sql.*;
import java.io.*;
/**
 *
 * @author SAGAR
 */
public class ImagEx2 {
    public static void main(String args[]) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root", "root");   
            File file1=new File("D:\\mona.jpg");
            FileOutputStream fos=new FileOutputStream(file1);
            byte b[];
            Blob blob;
            PreparedStatement ps=con.prepareStatement("select * from imageex");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                blob=rs.getBlob("image");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);
            }
            ps.close();
            fos.close();
            con.close();
            System.out.println("image retrieved successfully");
            } catch (Exception ee) {
                System.out.println(ee.toString());
            }
        }
}