<%--
  Created by IntelliJ IDEA.
  User: SAGAR
  Date: 22-02-2022
  Time: 02:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>NevBar</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="nevbar.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.531/jquery.min.js">
</head>
<body>

<div class="wrapper">
    <div class="sidebar">
        <h3><i class="fa fa-bars"></i></h3>
        <a class ="sub-btn" href="Regester.jsp"><i class="fa fa-address-book">&emsp;</i><b>Add Record</b><i class="fa fa-angle-right dropdown"></i></a>

        <a class ="sub-btn" href="Update.jsp"><i class="fa fa-pencil-square-o">&emsp;</i><b>Update Record</b><i class="fa fa-angle-right dropdown"></i></a>

        <a class ="sub-btn" href="#"><i class="fa fa-search">&emsp;</i><b>Find Record</b><i class="fa fa-angle-right dropdown"></i></a>

        <a class ="sub-btn" href="#"><i class="fa fa-trash">&emsp;</i><b>Delete Record</b><i class="fa fa-angle-right dropdown"></i></a>

    </div>
    <div class="main_content">
        <div class="header">
            <ul class="breadcrumb">
                <%
                    String uname=(String)session.getAttribute("logname");
                %>
                <li>WELCOME <%=uname.toUpperCase()%> </li>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;
                <li><a href="#"><i class="fa fa-info-circle">&emsp;</i>PROFILE</a>&emsp;&emsp;</li>
                <li><a href="login.jsp"><i class="fa fa-sign-out">&emsp;</i>LOGOUT</a></li>
            </ul>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Username</th>
                <th scope="col">Password</th>
                <th scope="col">City</th>
                <th scope="col">Address</th>
                <th scope="col">Phone</th>
            </tr>
            </thead>
            <%
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/jsp","root","root");
                Statement st = con.createStatement();
                String query = "select * from studcrud";
                ResultSet rs =  st.executeQuery(query);

                while(rs.next())
                {
            %>
            <tbody>
            <tr>
                <td><%=rs.getString("id") %></td>
                <td><%=rs.getString("username") %></td>
                <td><%=rs.getString("password") %></td>
                <td><%=rs.getString("city") %></td>
                <td><%=rs.getString("address") %></td>
                <td><%=rs.getString("phone") %></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
