<%-- 
    Document   : show_message.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cse.maven_webmail.model.DataAccessObject" %>
<%@page import="cse.maven_webmail.beans.InboxBean" %>
<!DOCTYPE html>

<jsp:useBean id="pop3" scope="page" class="cse.maven_webmail.model.Pop3Agent" />
<%
            pop3.setHost((String) session.getAttribute("host"));
            pop3.setUserid((String) session.getAttribute("userid"));
            pop3.setPassword((String) session.getAttribute("password"));
            InboxBean inbox = InboxBean.getInstance();
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>메일 보기 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css?ver=1" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_previous_menu.jsp" />
        </div>

        <div id="msgBody">
            <%= pop3.getMessage(Integer.parseInt((String) request.getParameter("msgid")))%>
           <% inbox.setMsg_id( Integer.parseInt(request.getParameter("msgid")));
              inbox.setSubject( request.getParameter("subject"));
              inbox.setMessage_body( request.getParameter("message"));
            %>
        </div>
    <center>
    
        <a href ="./answer_mail.jsp?msgid=<%=request.getParameter("msgid")%>">
            [답장]
        </a> &nbsp;&nbsp;
        <a href ="./relay_mail.jsp?msgid=<%=request.getParameter("msgid")%>">
            [전달]
        </a> &nbsp;&nbsp;
    </center>
        
        <jsp:include page="footer.jsp" />
        
        
    </body>
</html>
