/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransactionPackage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
/**
 *
 * @author SAGAR
 */
public class TransationEx2 {
    public static void main(String[] arg) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sagar","root","root");
        con.setAutoCommit(false);
        PreparedStatement ps=con.prepareStatement("insert into user (username,password) values(?,?)");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String ans="";
        do{

            System.out.println("enter username");
            String name=br.readLine();
            
            System.out.println("enter password");
            String pass=br.readLine();
          
            ps.setString(1,name);   
            ps.setString(2,pass);
            ps.executeUpdate();
            System.out.println("commit/rollback");
            String answer=br.readLine();
            if(answer.equals("commit")){
                con.commit();
            }
            if(answer.equals("rollback")){
                con.rollback();
            }
            
            System.out.println("Want to add more records y/n");
            ans=br.readLine();
                if(ans.equals("n")){
                    break;
                }
        }while(ans.equals("y"));
            con.commit();
            System.out.println("record successfully saved");
            con.close();
        }catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}