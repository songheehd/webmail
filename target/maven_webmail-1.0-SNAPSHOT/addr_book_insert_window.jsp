<%-- 
    Document   : addr_book_insert
    Created on : 2019. 6. 1., 오후 8:05:35
    Author     : LeeJH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css?ver=1" />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        
        <div id="sidebar">
            
            <jsp:include page="sidebar_menu.jsp" />
            
        </div>
        
        <h1>주소 등록</h1>
        <form action="addr_book_insert.jsp" method="get" id="addrinsert">
            <table border="0">
                <thead>
                    <tr>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>이름</td>
                        <td><input type="text" name="addr_name" size="20"/></td>
                    </tr>
                    <tr>
                        <td>이메일</td>
                        <td><input type="text" name="addr_email" size="20"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <center>
                                <input type="submit" value="등록"/>
                                <input type="reset" value="초기화" />
                                <input type="button" value="취소" onclick="history.back();"/>
                            </center>
                        </td>
                    </tr>
                </tbody>
            </table>
        
        
        <jsp:include page="footer.jsp" />
    </body>
</html>
