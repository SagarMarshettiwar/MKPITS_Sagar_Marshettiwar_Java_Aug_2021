<%-- 
    Document   : Regester
    Created on : 2 Feb, 2022, 7:11:43 PM
    Author     : SAGAR
--%>

<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if(request.getParameter("submit") != null){
        String Name=request.getParameter("uname");
        String Password=request.getParameter("upass");
        String City=request.getParameter("city");
        String Address=request.getParameter("address");
        String PhoneNo=request.getParameter("phone");
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost/sagar","root","root");
        PreparedStatement pstmt=con.prepareStatement("insert into jsprec values (?,?,?,?,?)");
        pstmt.setString(1,Name);
        pstmt.setString(2,Password);
        pstmt.setString(3,City);
        pstmt.setString(4,Address);
        pstmt.setString(5,PhoneNo);
        pstmt.executeUpdate();
    %>
        <script>
            alert("Record Inserted.............");
        </script>
    <%
        }
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Regester Page</title>
    </head>
    <body>
        <form method="post" action="#">
            <lable>Username</lable>
            <input type="text" name="uname"><br>
            <lable>Password</lable>
            <input type="Password" name="upass"><br>
            <lable>City</lable>
            <input type="text" name="city"><br>
            <lable>Address</lable>
            <input type="text" name="address"><br>
            <lable>Phone no</lable>
            <input type="text" name="phone"><br>
            <input type="Submit" name="submit">
            <a href="Login.jsp" >Go Back</a>
        </form>
    </body>
</html>
