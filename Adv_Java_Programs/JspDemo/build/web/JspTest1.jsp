<%-- 
    Document   : JspTest1
    Created on : 1 Feb, 2022, 12:42:15 PM
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
        <h1>Hello World!</h1>
        <%
            int a=10,b=10,c;
                c=a+b;
                out.println(c);
            %>
            <br>
            <%="Ans="+c %>
    </body>
</html>
