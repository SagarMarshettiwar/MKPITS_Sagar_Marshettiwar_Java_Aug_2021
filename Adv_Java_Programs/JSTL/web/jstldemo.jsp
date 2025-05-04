<%-- 
    Document   : jstldemo
    Created on : 4 Feb, 2022, 11:14:39 AM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <s:set var="num1" value="34"></s:set>
        <s:out value="${num1}"></s:out>
        
        <s:if test="${num1>33}">
            <h1>num 1 greater</h1>
        </s:if>
      
         
        <s:forEach var="i" begin="1" end="10">
            <h1>Hello World!</h1>
        </s:forEach>   
    </body>
</html>
