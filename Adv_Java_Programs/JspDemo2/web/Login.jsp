<%-- 
    Document   : Login
    Created on : 2 Feb, 2022, 7:09:43 PM
    Author     : SAGAR
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if(request.getParameter("submit") != null){
        String name=request.getParameter("uname");
        String pass=request.getParameter("upass");
        
       Class.forName("com.mysql.cj.jdbc.Driver");
       Connection con=DriverManager.getConnection("jdbc:mysql://localhost/sagar","root","root");
       PreparedStatement  pstmt=con.prepareStatement("select * from jsprec where username= ? and password= ?");
       pstmt.setString(1, name);
       pstmt.setString(2, pass);
       ResultSet rs= pstmt.executeQuery();
       if(rs.next())
       {
           response.sendRedirect("Success.jsp");
       }else{
            response.sendRedirect("Error.jsp");
        }
    }
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <form method="post" action="#">
            <lable>Username</lable>
            <input type="text" name="uname"><br>
            <lable>Password</lable>
            <input type="Password" name="upass"><br>
            <input type="Submit" name="submit">
            <a href="Regester.jsp"> Create Account </a> 
        </form>   
    </body>
</html>
