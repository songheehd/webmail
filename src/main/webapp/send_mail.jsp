<%-- 
    Document   : send_mail.jsp
    Created on : 2019. 5. 23., 오후 2:48:31
    Author     : minyeong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>보낸 메일함</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css?ver=1" />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        
        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>
        
        <h1>보낸 메일함입니다.</h1>
        
        <jsp:include page="footer.jsp" />
    </body>
</html>
