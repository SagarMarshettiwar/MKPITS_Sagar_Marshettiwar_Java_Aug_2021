<%-- 
    Document   : AddNumber
    Created on : 4 Feb, 2022, 2:37:00 PM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s" %>
<%
        if(request.getParameter("submit") != null){
            int n1=Integer.parseInt(request.getParameter("num1"));
            int n2=Integer.parseInt(request.getParameter("num2"));
%>

        <s:set  var="num1" value="<%=n1 %>" ></s:set>
        <s:set  var="num2" value="<%=n2 %>"></s:set>
        
        <s:out value="${num1+num2}"></s:out>  
        
 <%
     }
  %>   
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add</title>
    </head>
    <body>
        <form action="#" method="post">
            <table>
                <tr>
                    <td>number1</td>
                    <td><input type="text" name="num1"></td>
                </tr>
                 <tr>
                    <td>number2</td>
                    <td><input type="text" name="num2"></td>
                </tr>
                <tr>
                    <td><input type="Submit" name="submit"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
