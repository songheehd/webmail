<%-- 
    Document   : addr_book
    Created on : 2019. 5. 30., 오전 11:36:17
    Author     : LeeJH
--%>



<%@page import="cse.maven_webmail.beans.addrbookBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cse.maven_webmail.model.DataAccessObject"%>
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
        
        <h1>주소록</h1>
        <table border ="1" width="600">
            <tr>
                <td>이름</td>
                <td>이메일</td>
                <a href="addr_book_insert_window.jsp">주소록 추가</a>
            </tr>
            <%
               String userid = (String)session.getAttribute("userid");

               
               ArrayList<addrbookBean>list=DataAccessObject.getInstance().addrList(userid);
               for(addrbookBean bean:list){
                   
            %>
            <tr>

                <td><%=bean.getAddrname()%></td>
                <td><%=bean.getAddrmail()%></td>
                <td><a href="write_mail.jsp?recv=<%=bean.getAddrmail()%>">메일보내기</a></td>
                <td><a href="addr_book_delete.jsp?id=<%=bean.getId()%>">삭제</a></td>
               
                
            </tr>
            <%
               }
             %>
           
                            
                
        </table>
        
        <jsp:include page="footer.jsp" />
    </body>
</html>

