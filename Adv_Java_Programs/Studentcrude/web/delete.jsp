<%-- 
    Document   : delete
    Created on : 12 May, 2021, 8:20:24 PM
    Author     : SAGAR
--%>

<%@page import="java.sql.*" %> 


<% 
        String id = request.getParameter("id");
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/sagar","root","root");
        pst = con.prepareStatement("delete from records where id = ?");
        pst.setString(1, id);
        pst.executeUpdate();
        
        %>
        
        <script>
            
            alert("Record Deletee");
            
       </script>
