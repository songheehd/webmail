<%-- 
    Document   : sidebar_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.control.CommandType" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>웹메일 시스템 메뉴</title>
<!--        <style type="text/css">
            a:link {text-decoration: none; }
            a:hover {background: white; text-decoration: underline;}
        </style>-->
    </head>
    <body>
        <br> <br>
        
        <span style="color: indigo"> <b>사용자: <%= session.getAttribute("userid") %> </b> </span> <br>

        <p> <a href="write_mail.jsp" style="color:black"> 메일 쓰기 </a> </p>
        <p> <a href="important_mail.jsp" style="color:black"> 중요 메일함 </a> </p>
        <p> <a href="send_mail.jsp" style="color:black"> 보낸메일함 </a> </p>
        <p> <a href="addr_book.jsp" style="color:black"> 주소록 </a> </p>
        <p> <a href="trash_mail.jsp" style="color:black"> 휴지통 </a> </p>
        <p><a href="Login.do?menu=<%= CommandType.LOGOUT %>" style="color: red; text-decoration: none">로그아웃</a></p>
    </body>
</html>
