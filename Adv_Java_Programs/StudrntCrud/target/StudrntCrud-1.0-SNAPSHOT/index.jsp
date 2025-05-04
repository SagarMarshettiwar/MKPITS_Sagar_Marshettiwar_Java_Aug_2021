<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

    if(request.getParameter("submit") != null) {
        String name = request.getParameter("uname");
        String pass = request.getParameter("upass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sagar", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("select * from jsprec where username= ? and password= ?");
            pstmt.setString(1, name);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                response.sendRedirect("Dashbord.jsp");
                session.setAttribute("logname", name);
            } else {
                response.sendRedirect("Error.jsp");
            }
        } catch (Exception e) {
            out.println(e);
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Form</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="login.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="main">
    <form action="#" method="post">
        <h1 align="center"> <i class="fa fa-sign-in"></i>&emsp; <lable>SIGN IN </lable> </h1>
        <hr>
        <div class="inset">
            <p>
                <label for="userName"><b>USER NAME</b></label><br>
                <i class="fa fa-user"></i>
                <input type="text" name="uname" placeholder="User Name" size="30">
            </p>
            <p>
                <label for="password"><b>PASSWORD</b></label> &emsp;&emsp;&emsp;&emsp;<a href="#" >Lost password ?</a><br>
                <i class="fa fa-unlock-alt"></i>
                <input type="password" name="upass" placeholder="password" size="30">
            </p>
            <p>
                <input type="checkbox" name="remember" id="remember">
                <label for="remember">remember me</label>
            </p>
        </div>
        <p class="button">
            <input type="submit" name="submit" value="Login">
        </p>
    </form>
</div>
</div>
</body>
</html>