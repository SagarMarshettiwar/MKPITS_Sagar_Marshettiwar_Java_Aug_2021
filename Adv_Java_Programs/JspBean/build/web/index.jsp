<%-- 
    Document   : index
    Created on : 4 Feb, 2022, 4:10:18 PM
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
        <form action="Disp.jsp" method="post">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="name"></td>
                </tr>
                 <tr>
                    <td>Password</td>
                    <td><input type="text" name="password"></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><input type="text" name="address"></td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input type="text" name="phone"></td>
                </tr>
                <tr>
                    <td><input type="Submit" name="submit"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
