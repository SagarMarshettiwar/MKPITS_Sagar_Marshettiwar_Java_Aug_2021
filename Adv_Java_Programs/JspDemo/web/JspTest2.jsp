<%-- 
    Document   : JspTest2
    Created on : 1 Feb, 2022, 2:53:32 PM
    Author     : SAGAR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page 2</title>
    </head>
    <body>
        <%
            String col[]={"red","blue","green","yellow","purple","black","pink","grey","lightgreen","orange"};
            for(int i=0;i<10;i++)
            {
                
            %>
            
            <font color="<%=""+col[i] %>" size="12"> <%= i %> </font><br>
            
            <%
                
                }          
        %>
    </body>
</html>
