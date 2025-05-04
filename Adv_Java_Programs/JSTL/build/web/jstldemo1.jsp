<%-- 
    Document   : jstldemo1
    Created on : 4 Feb, 2022, 12:03:07 PM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <s:set  var="num1" value="19"></s:set>
        <s:set  var="num2" value="25"></s:set>
        
        <s:out value="${num1+num2}"></s:out>
        
        <s:choose>
            <s:when test="${num1>20}">
                 <h1>greater</h1>
            </s:when>
            <s:otherwise>
                <h1>Smaller</h1>
            </s:otherwise> 
        </s:choose>
        <s:catch var="exob">
            <% int c=2/0; %>
        </s:catch>
           <s:out value="${exbo}"></s:out>     
                
    </body>
</html>
