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
        <title>주소 등록</title>
    </head>
    <body>
        <%
            String userid = (String)session.getAttribute("userid");
            String name = request.getParameter("addr_name");
            String email = request.getParameter("addr_email");
            
            DataAccessObject.getInstance().addrInsert(userid, name, email);

            
            response.sendRedirect("addr_book.jsp");
            
        %>
        <span style="color: indigo"> <b>사용자: <%= session.getAttribute("userid") %> </b> </span> <br>
    </body>
</html>
