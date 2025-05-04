<%--
  Created by IntelliJ IDEA.
  User: SAGAR
  Date: 22-02-2022
  Time: 02:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    if(request.getParameter("submit") != null){
        String name=request.getParameter("uname");
        String pass=request.getParameter("upass");
        String city=request.getParameter("city");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost/jsp","root","root");
        PreparedStatement  pstmt=con.prepareStatement("insert into studcrud (username,password,city,address,phone) values (?,?,?,?,?)");
        pstmt.setString(1, name);
        pstmt.setString(2, pass);
        pstmt.setString(3, city);
        pstmt.setString(4, address);
        pstmt.setString(5, phone);
        pstmt.executeUpdate();
%>
<script>
    alert("Record Added....................");
</script>
<%
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Regestration</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.531/jquery.min.js">
</head>
<body>
<br><br>
<h1 style="text-align:center"><b><i>ADD RECORDS</i> </b></h1>
<br><br>
<div class="row" style="margin-left: 35%" >
    <div class="col-lg-6" >
        <form  method="POST" action="#" >

            <div alight="left">
                <label class="form-label">UserName</label>
                <input type="text" class="form-control" placeholder="User Name" name="uname" id="uname" required >
            </div>

            <div alight="left">
                <label class="form-label">Password</label>
                <input type="text" class="form-control" placeholder="Password" name="upass" id="upass" required >
            </div>

            <div alight="left">
                <label class="form-label">City</label>
                <input type="text" class="form-control" placeholder="City" name="city" id="city" required >
            </div>

            <div alight="left">
                <label class="form-label">Address</label>
                <input type="text" class="form-control" placeholder="Address" name="address" id="address" required >
            </div>

            <div alight="left">
                <label class="form-label">Phone</label>
                <input type="text" class="form-control" placeholder="Phone" name="phone" id="phone" required >
            </div>
            </br>

            <div alight="left">
                <input type="submit" id="submit" value="submit" name="submit" class="btn btn-info">
                <input type="reset" id="reset" value="reset" name="reset" class="btn btn-warning">
                <a href="Dashbord.jsp" >go to dashbord </a>
            </div>

        </form>
    </div>
</div>
</body>
</html>

