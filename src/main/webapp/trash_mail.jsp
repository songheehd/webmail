<%-- 
    Document   : trash_mail
    Created on : 2019. 5. 9, 오후 2:12:07
    Author     : user
--%>

<%@page import="cse.maven_webmail.beans.InboxBean"%>
<%@page import="java.util.List"%>
<%@page import="cse.maven_webmail.model.DataAccessObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>메일 휴지통</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>

    <body>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>
    <div id="main">
      
            <%

            DataAccessObject.getInstance().setUserid(session.getAttribute("userid"));
            List<InboxBean> list =   (List<InboxBean>)request.getAttribute("list");
             %>
            <% if( list != null){ %> 
                           
            <%                
            for (InboxBean inbox : list){
                %>
       
      <h2>웹메일 조회 결과</h2>
            <table border= "1">
                  <tr>
                        <td>보낸사람 : </td>
                        <td> <% if( inbox != null){ %> 
                            <%=inbox.getSender()%> <%}%></td>
                        
                  </tr>
                  <tr>
                        <td>제목 : </td>
                        <td> <% if( inbox != null){ %> 
                            <%=inbox.getMessage_body()%> <%}%></td>
                  </tr>
                  
            </table>
      <%
            }
        }
        else{
               System.out.println("휴지통리스트가"
+ ""
+ ""
+ "없어ㅓㅓㅓㅓㅓㅓ"
+ "ㅓㅓㅓㅓㅓ"
+ "ㅓㅓㅓㅓㅓ"
+ "요요요");
                }

      %>


        
        </div>

        
        <jsp:include page="footer.jsp" />

    </body>
</html>
