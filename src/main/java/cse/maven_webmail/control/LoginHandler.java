/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.control;

import cse.maven_webmail.beans.UserBean;
import cse.maven_webmail.model.DataAccessObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cse.maven_webmail.model.Pop3Agent;

/**
 *
 * @author jongmin
 */
public class LoginHandler extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String ADMINISTRATOR = "admin";
    // 임의로 관리자 계정 admin으로 선언
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
//        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int selected_menu = Integer.parseInt((String) request.getParameter("menu"));
        DataAccessObject dao = DataAccessObject.getInstance();
        UserBean userBean = new UserBean();
         
        try {
            DataAccessObject.getInstance().initalize("com.mysql.jdbc.Driver", "webmail", "2007", "jdbc:mysql://113.198.236.111:12122/webmail?useUnicode=true&characterEncoding=euckr");
            HttpSession session = request.getSession();
            
            switch (selected_menu) {
                case CommandType.LOGIN:
                    // 로그인 했을 경우
                    String host = (String) request.getSession().getAttribute("host");
                    String userid = request.getParameter("userid");
                    String password = request.getParameter("passwd");
                            
                    // 로그인한 사용자 정보 UserBean 저장
                    userBean.setUserid(userid);
                    userBean.setPassword(password);
                   
                    String user_id = userBean.getUserid();
                    
                    System.out.println("-----------" + userid);
                    dao.msgid_update(user_id);
                    // Check the login information is valid using <<model>>Pop3Agent.
                    Pop3Agent pop3Agent = new Pop3Agent(host, userid, password);
                    boolean isLoginSuccess = pop3Agent.validate();
                    // Pop3Agent 객체를 생성하여 로그인이 성공 여부 확인
//                    boolean isLoginSuccess = false;
                    pop3Agent.getMessageList();

                    // Now call the correct page according to its validation result.
                    if (isLoginSuccess) {
                        if (isAdmin(userid)) {
                            // index.jsp에서 전달받은 userid가 관리자인지 사용자인지 check
                            // HttpSession 객체에 userid를 등록해 둔다.
//                            DataAccessObject.getInstance().setUserid(userid);
                            session.setAttribute("userid", userid);
                            response.sendRedirect("admin_menu.jsp");
                            // 관리자일 경우 관리자 화면 admin_menu.jsp 로 이동
                        } else {
                            // HttpSession 객체에 userid와 password를 등록해 둔다.
                            session.setAttribute("userid", userid);
                            session.setAttribute("password", password);
                            response.sendRedirect("main_menu.jsp?page=1");
                            // 사용자일 경우 사용자 화면 main_menu.jsp로 이동
                        }
                    } else {
                        RequestDispatcher view = request.getRequestDispatcher("login_fail.jsp");
                        // 로그인에 실패할 경우 login_fail.jsp 로 이동
                        view.forward(request, response);
//                        response.sendRedirect("login_fail.jsp");
                    }
                    break;
                case CommandType.LOGOUT:
                    // 로그아웃 할 경우
                    out = response.getWriter();
                    session.invalidate();
//                    response.sendRedirect(homeDirectory);
                    response.sendRedirect(getServletContext().getInitParameter("HomeDirectory"));
                    // 초기화면으로 이동
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            System.err.println("LoginCheck - LOGIN error : " + ex);
        } finally {
            out.close();
        }
    }

    protected boolean isAdmin(String userid) {
        boolean status = false;

        if (userid.equals(this.ADMINISTRATOR)) {
            // userid가 admin 일 경우 true 반환
            status = true;
        }

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
            throws ServletException,
            IOException {
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
            throws ServletException,
            IOException {
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
