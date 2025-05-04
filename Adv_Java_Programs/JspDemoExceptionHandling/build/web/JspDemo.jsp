<%-- 
    Document   : JspDemo
    Created on : 3 Feb, 2022, 11:51:10 AM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>Error Page</h1>
        <%=exception%>
    </body>
</html>
