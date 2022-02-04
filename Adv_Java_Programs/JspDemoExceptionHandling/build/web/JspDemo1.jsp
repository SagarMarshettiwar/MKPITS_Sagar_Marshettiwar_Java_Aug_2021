<%-- 
    Document   : JspDemo1
    Created on : 3 Feb, 2022, 12:00:34 PM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="JspDemo.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>demo2</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
             int c=5/0;
            %>
    </body>
</html>
