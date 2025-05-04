<%-- 
    Document   : Disp
    Created on : 4 Feb, 2022, 4:15:02 PM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="obj" class="com.BeanDemo.Demo1"></jsp:useBean>
        <jsp:setProperty name="obj" property="*"></jsp:setProperty>
        
        <h1>My name = <jsp:getProperty name="obj" property="name"></jsp:getProperty></h1>
        <h1>my password = <jsp:getProperty name="obj" property="password"></jsp:getProperty></h1>
        <h1>my Address = <jsp:getProperty name="obj" property="address"></jsp:getProperty></h1>
        <h1>my phone no = <jsp:getProperty name="obj" property="phone"></jsp:getProperty></h1>
    </body>
</html>
