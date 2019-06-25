/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import cse.maven_webmail.model.DataAccessObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cse.maven_webmail.model.Pop3Agent;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author jongmin
 */


@WebServlet(name = "ReadMailHandler", urlPatterns = {"/ReadMail.do"})
public class ReadMailHandler extends HttpServlet {

    private final String homeDirectory = "/maven_webmail/";
    private final String uploadTempDir = "C:/temp/upload";
    private final String uploadTargetDir = "C:/temp/upload";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        PrintWriter out = null;
        DataAccessObject dao = DataAccessObject.getInstance();
        
        try {
            request.setCharacterEncoding("UTF-8");
            int select = Integer.parseInt((String) request.getParameter("menu"));
            int msg_id = Integer.parseInt(request.getParameter("msgid"));
            String userid = request.getParameter("userid");
            out.println("<p>선택한 메뉴 번호는 " + select + "입니다.</p>");

            switch (select) {
                case CommandType.TRASH_MENU:
                    dao.trashBox_update(userid, msg_id);
                    out = response.getWriter();
                    out.println(getTrashBoxMovePopUp());
                    break;
                    
                case CommandType.DELETE_MAIL_COMMAND:
                    out = response.getWriter();
                    deleteMessage(request);
                    response.sendRedirect("main_menu.jsp?page=1");
                    break;

                case CommandType.DOWNLOAD_COMMAND: // 파일 다운로드 처리
                    download(request, response);
                    break;

                default:
                    out = response.getWriter();
                    out.println("없는 메뉴를 선택하셨습니다. 어떻게 이 곳에 들어오셨나요?");
                    break;
            }
        } catch (Exception ex) {
            out.println(ex.toString());
        } 
        finally {
            out.close();
        }
    }
    private String getTrashBoxMovePopUp() {
        String alertMessage = "휴지통으로 이동하였습니다.";
        StringBuilder successPopUp = new StringBuilder();
        successPopUp.append("<html>");
        successPopUp.append("<head>");

        successPopUp.append("<title>휴지통 이동</title>");
        successPopUp.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/main_style.css\" />");
        successPopUp.append("</head>");
        successPopUp.append("<body onload=\"goMainMenu()\">");
        successPopUp.append("<script type=\"text/javascript\">");
        successPopUp.append("function goMainMenu() {");
        successPopUp.append("alert(\"");
        successPopUp.append(alertMessage);
        successPopUp.append("\"); ");
        successPopUp.append("window.location = \"main_menu.jsp?page=1\"; ");
        successPopUp.append("}  </script>");
        successPopUp.append("</body></html>");
        return successPopUp.toString();
    }

    private void download(HttpServletRequest request, HttpServletResponse response) { //throws IOException {
        response.setContentType("application/octet-stream");

        ServletOutputStream sos = null;

        try {
            /* TODO output your page here */
            request.setCharacterEncoding("UTF-8");
            // LJM 041203 - 아래와 같이 해서 한글파일명 제대로 인식되는 것 확인했음.
            String fileName = request.getParameter("filename");
            System.out.println(">>>>>> DOWNLOAD: file name = " + fileName);
            // fileName에 있는 ' '는 '+'가 파라미터로 전송되는 과정에서 변한 것이므로
            // 다시 변환시켜줌.
//            fileName = fileName.replaceAll(" ", "+");
            String userid = request.getParameter("userid");
            //String fileName = URLDecoder.decode(request.getParameter("filename"), "utf-8");

            // download할 파일 읽기

            // LJM 090430 : 수정해야 할 부분 - start ------------------
            // 리눅스 서버 사용시
            //String downloadDir = "/var/spool/webmail/download/";

            // 윈도우즈 환경 사용시
            String downloadDir = "C:/temp/download/";
            // LJM 090430 : 수정해야 할 부분 - end   ------------------

//            response.setHeader("Content-Disposition", "attachment; filename=" +
//                    MimeUtility.encodeText(fileName) + ";");

//            response.setHeader("Content-Disposition", "attachment; filename=" +
//                    MimeUtility.encodeText(fileName, "UTF-8", "B") + ";");

            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8") + ";");

            File f = new File(downloadDir + userid + "/" + fileName);
            byte[] b = new byte[(int) f.length()];
            FileInputStream fis = new FileInputStream(f);
            fis.read(b);

            // 다운로드
            sos = response.getOutputStream();
            sos.write(b);
            sos.flush();
            sos.close();
        } catch (Exception ex) {
            System.out.println("====== DOWNLOAD exception : " + ex);
        } finally {
            // 다운로드후 파일 삭제
            //f.delete();
        }
    }

    private boolean deleteMessage(HttpServletRequest request) {
        int msgid = Integer.parseInt((String) request.getParameter("msgid"));

        HttpSession httpSession = request.getSession();
        String host = (String) httpSession.getAttribute("host");
        String userid = (String) httpSession.getAttribute("userid");
        String password = (String) httpSession.getAttribute("password");

        Pop3Agent pop3 = new Pop3Agent(host, userid, password);
        boolean status = pop3.deleteMessage(msgid, true);
        return status;
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
