<%-- 
    Document   : relay_mail
    Created on : 2019. 5. 23., 오후 4:53:25
    Author     : minyeong
--%>

<%@page import="cse.maven_webmail.beans.InboxBean"%>
<%@page import="cse.maven_webmail.model.DataAccessObject"%>
<%@page import="cse.maven_webmail.control.CommandType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>전달</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
     <body>
        <jsp:useBean id="pop3" scope="page" class="cse.maven_webmail.model.Pop3Agent" />
        <%
            pop3.setHost((String) session.getAttribute("host"));
            pop3.setUserid((String) session.getAttribute("userid"));
            pop3.setPassword((String) session.getAttribute("password"));
            InboxBean inbox = InboxBean.getInstance();
%>
        <jsp:include page="header.jsp" />

        <div id="sidebar">
            <jsp:include page="sidebar_previous_menu.jsp" />
        </div>

        <div id="main">
            <%-- <jsp:include page="mail_send_form.jsp" /> --%>
            <form enctype="multipart/form-data" method="POST"
                   action="WriteMail.do?menu=<%= CommandType.SEND_MAIL_COMMAND%>" >
              
                <table>
                    <tr>
                        <td> 수신 </td>
                        <td> <input type="text" name="to" size="80"  id ="sender"
                                    value= <%=DataAccessObject.getInstance().getSender(session.getAttribute("userid"),InboxBean.getInstance().getMsg_id()) %>  </td>
                    </tr>
                    <tr>
                        <td>참조</td>
                        <td> <input type="text" name="cc" size="80">  </td>
                    </tr>
                    <tr>
                        <td> 메일 제목 </td>
                        <td> <input type="text" name="subj" size="80" 
                                      value= <%=DataAccessObject.getInstance().getSubject(session.getAttribute("userid"),InboxBean.getInstance().getMsg_id())%> </td>
                    </tr>
                    <tr>
                        <td colspan="2">본  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 문</td>
                    </tr>
                    <tr>  <%-- TextArea    --%>
                        <td colspan="2">  <textarea rows="15" name="body" cols="80"
                                                     value= <%=pop3.getMessage(inbox.getMsg_id())%>></textarea> </td>
                    </tr>
                    <tr>
                        <td>첨부 파일</td>
<!--                        <td> <input type="file" name="file1"  size="80">  </td>-->
                        <input type="hidden" name="count" >
                          
                            <td size="60" >내용</td>
                            <td size="20"><input type="button" value="행 추가" onclick="javascript:addInputBox();"> </td>
                </tr>
                </table>
                <center>    <table  id="dynamic_table" > </table> </center>

                          
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="메일 보내기" >
                            <input type="reset" value="다시 입력">
                        </td>
                    </tr>
              
    <tr>
  
    </tr>
    </table>
       
            </form>
        </div>
        <script language="javascript">
            var count = 1;
            var addCount;

           //행추가
           function addInputBox() {
           if(count<4){
                for(var i=1; i<=count; i++) {
                 if(!document.getElementsByName("file"+i)[0]) {
                  addCount = i;
                  break;
                 }
                 else addCount = count; 
                }

            var addStr = "<tr><td width=80><input type=file name='file"+(addCount)+"' size=80 ></td></tr>";
            var table = document.getElementById("dynamic_table");
            var newRow = table.insertRow();
            var newCell = newRow.insertCell();
            newCell.innerHTML = addStr;
            count++;
            }
            else {alert("3개까지만 첨부하세요");}
           }
           
         </script>
    </body>
</html>
