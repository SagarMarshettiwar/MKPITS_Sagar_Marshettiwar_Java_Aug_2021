<%-- 
    Document   : JspForm
    Created on : 1 Feb, 2022, 4:15:34 PM
    Author     : SAGAR
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("Submit") != null){
        String name=request.getParameter("uname");
        String pass=request.getParameter("upass");
        
       Class.forName("com.mysql.cj.jdbc.Driver");
       Connection con=DriverManager.getConnection("jdbc:mysql://localhost/sagar","root","root");
       PreparedStatement  pstmt=con.prepareStatement("select * from userlogin where name= ? and password= ?");
       pstmt.setString(1, name);
       pstmt.setString(2, pass);
       ResultSet rs= pstmt.executeQuery();
       if(rs.next())
       {
           response.sendRedirect("JspTest1.jsp");
       }else{
            response.sendRedirect("JspTest2.jsp");
        }
    }
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
            <form method="POST" action="#">
            Name<input type="text" name="uname"><br> 
            Password<input type="password" name="upass"><br>
            <input type="submit" name="Submit">
        </form>
    </body>
</html>
