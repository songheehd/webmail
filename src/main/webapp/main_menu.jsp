<%-- 
    Document   : main_menu.jsp
    Author     : jongmin
--%>

<%@page import="cse.maven_webmail.control.CommandType"%>
<%@page import="cse.maven_webmail.beans.MainListBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cse.maven_webmail.model.DataAccessObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<jsp:useBean id="pop3" scope="page" class="cse.maven_webmail.model.Pop3Agent" />
<%
            //String pageno = (String) request.getParameter("pageno");
            //if (pageno != null) {
            //    session.setAttribute("pageno", pageno);
            //}
            pop3.setHost((String) session.getAttribute("host"));
            
            String userid = (String) session.getAttribute("userid");
            pop3.setUserid(userid);
            pop3.setPassword((String) session.getAttribute("password"));
            //pop3.setPageno((int)Integer.parseInt((String)session.getAttribute("pageno")));
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주메뉴 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>

    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>
    <center>
        <div id="main">
            <table>
                <tr>
                    <td>보낸 사람</td>
                    <td>제목</td>
                    <td>보낸 날짜</td>
                    <td>삭제</td>
                </tr>
                
                <% 
                    DataAccessObject dao = DataAccessObject.getInstance();
                    dao.msgid_update(userid);
                    pop3.getMessageList();
                    ArrayList<MainListBean> main_list_paging  = new ArrayList();
                    int page_num = Integer.parseInt(request.getParameter("page").toString());
                    main_list_paging = dao.main_list(userid, page_num);
                    for(int i = 0; i < main_list_paging.size(); i++){
                        MainListBean mainListBean = (MainListBean)main_list_paging.get(i);
                   
                %>
               
                    <td>
                        <%= mainListBean.getSender()%>
                    </td>
                    <td id = subject>
                        <a href=show_message.jsp?msgid=<%= mainListBean.getMsg_id()%>> 
                            <%= mainListBean.getMessage_subject()%> 
                        </a>
                    </td>
                    <td>
                        <%= mainListBean.getMessage_date()%>
                    </td>
                    <td id = delete>
                        <a href=ReadMail.do?menu=<%=CommandType.TRASH_MENU%>&msgid=<%=mainListBean.getMsg_id()%>>삭제</a>
                    </td>
                </tr>
            <% 
                }
                main_list_paging.clear();
            %>
            </table>
        </div>            
        
            <% 
                int listTotalCount = dao.main_list_total(userid);
                System.out.println(listTotalCount);
                int endPage;
                endPage = ((int)Math.ceil(listTotalCount/(double)10));
                System.out.println(endPage);
                for(int i = 1; i <= endPage; i++ ){
            %> 
                    <a href="main_menu.jsp?page=<%= i %>">
                       <%= i %>
                    </a>
            <%  
                }
            %>
    </center>
        <jsp:include page="footer.jsp" />

    </body>
</html>