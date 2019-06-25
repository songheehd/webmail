<%-- 
    Document   : addr_book_delete
    Created on : 2019. 5. 30., 오후 2:05:41
    Author     : LeeJH
--%>

<%@page import="cse.maven_webmail.model.DataAccessObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.beans.addrbookBean"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소 삭제</title>
    </head>
    <body>
        <%
            String id = request.getParameter("id");
            int n = 0;
            
            DataAccessObject.getInstance().addrDelete(id, n);
             
            response.sendRedirect("addr_book.jsp");
            
        %>
    </body>
</html>
