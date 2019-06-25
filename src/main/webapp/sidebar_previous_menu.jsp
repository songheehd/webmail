<%-- 
    Document   : sidebar_adduser_menu
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            a:link {text-decoration: none; }
            a:hover {background: white; text-decoration: underline;}
        </style>
    </head>
    <body>
        <br> <br> 

        <span style="color: indigo">
            <b>사용자: <%= session.getAttribute("userid") %> </b>
        </span> <br> <br>
        
        <a href="main_menu.jsp?page=1" style="color:black"> 이전 메뉴로 </a>
    </body>
</html>
