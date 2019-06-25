/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.model;

import cse.maven_webmail.beans.InboxBean;
import cse.maven_webmail.beans.MainListBean;
import cse.maven_webmail.beans.addrbookBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Statement;

/**
 *
 * @author minyeong
 */
public class DataAccessObject {
    private static DataAccessObject dataAccessObject = null;
    
    private String jdbcDriver;
    private String jdbcUrl;
    private String user;
    private String password;

//    private Object userid;
    
    private DataSource dataSource =null;
    private Connection conn = null; //db 접속 객체
    private Statement stmt = null;
    private PreparedStatement pstmt = null; //sql 실행 객체
    
    ArrayList<InboxBean> inbox_list;
    ArrayList<MainListBean> main_list;
    
    private DataAccessObject(){
    }
   
    public boolean initalize(String jdbcDriver, String user, String password, String jdbcUrl){
        try{
            this.jdbcDriver = jdbcDriver;
            this.user = user;
            this.password = password;
            this.jdbcUrl = jdbcUrl;
            //DB 연결
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(jdbcUrl, user, password); 
            System.out.println("Database Connect Success");
            
            inbox_list = new ArrayList<>();
            main_list = new ArrayList<>();
            
            return true;
        } catch(ClassNotFoundException cnfe){
            System.out.println("DataBase 드라이버 로딩 실패 :" + cnfe.toString());
        } catch(SQLException e){
            System.out.println("DataBase 접속 실패 :"+e.toString());
        } catch(Exception e){
            System.out.println("Unknown error : " + e);
            e.printStackTrace();
        }
        return false;
    }
    
    public static DataAccessObject getInstance(){
        if(dataAccessObject == null){
            dataAccessObject = new DataAccessObject();
        }
        return dataAccessObject;
    }
    
    public void msgid_update(String userid){
        inbox_list.clear();
        inbox_list = inbox_list(userid);
        String query = null;
              
        try{
            int msg_id_num = 1;
            for(InboxBean inbox : inbox_list){
                query= "update inbox set msg_id=" + msg_id_num +" where message_name ='"+inbox.getMessage_name()+"' and repository_name ='"+userid+"'";
                
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                
                msg_id_num++;
            }
        } catch (SQLException e) {
              e.printStackTrace();
        }
    }
    
    //데이터베이스 내용을 변수에 저장
    //정보 불러오는 메소드
   public ArrayList<InboxBean> inbox_list(String userid){
          String query = "select * from INBOX where repository_name  ='" + userid+"' and delete_check = 0 order by msg_id ";
          ResultSet rs = null;
          
        try {
             System.out.println(query);
             stmt = conn.createStatement();
             rs = stmt.executeQuery(query);
            
              while (rs.next()) {
                    String name = rs.getString(1);
                    String repository = rs.getString(2);
                    String state = rs.getString(3);
//                    String error = rs.getString(4);
                    String sender = rs.getString(5);
                    String recipients = rs.getString(6);
//                    String host = rs.getString(7);
//                    String addr = rs.getString(8);
                    String body = rs.getString(9);
                    String attribute = rs.getString(10);
//                    Date last_updated = rs.getDate(11);

                    //Inbox 생성
                    
                    InboxBean inboxBean = new InboxBean();
                    
                    inboxBean.setMessage_name(name);
                    inboxBean.setRepository_name(repository);
                    inboxBean.setMessage_state(state);
//                    inboxBean.setError_message(error);
                    inboxBean.setSender(sender);
                    inboxBean.setRecipients(recipients);
//                    Inbox.setRemote_host(host);
//                    Inbox.setRemote_addr(addr);
                    inboxBean.setMessage_body(body);
                    inboxBean.setMessage_attibutes(attribute);
                    //ArrayList에 추가
                    inbox_list.add(inboxBean);
             }
        } catch (SQLException e) {
              e.printStackTrace();
        } 
        return inbox_list;      //ArrayList 반환
  }
   
  public void update_inbox_head(String userid, int msg_id, String subject, String date){
      String query = "update inbox set message_subject='" + subject +"', message_date='" + date +"' where msg_id ='"+msg_id+"' and repository_name ='"+userid+"'";
       
      try{
         stmt = conn.createStatement();
         stmt.executeUpdate(query);
      }catch (SQLException e) {
              e.printStackTrace();
      }
  }
  
   public ArrayList<MainListBean> main_list(String userid, int page){;
                    
       int startNum = (page-1)*10;
       int endNum = page * 10;
       
       String query = "select msg_id, sender, message_subject, message_date from INBOX where repository_name ='" + userid+"' and delete_check = 0 order by msg_id desc limit ?,?";
       ResultSet rs = null;
          
        try {
             System.out.println(query);
             pstmt = conn.prepareStatement(query);
             pstmt.setInt(1, startNum);
             pstmt.setInt(2, endNum);
            
             rs = pstmt.executeQuery();
             
              while (rs.next()) {
                    int msg_id = rs.getInt(1);
                    String sender = rs.getString(2);
                    String message_subject = rs.getString(3);
                    String message_date = rs.getString(4);
                    
                    System.out.println("1111111111111111");
                    System.out.println("sender : "+ sender + "message_subject" + message_subject + "message_date" + message_date);
                    System.out.println("1111111111111");
                    
                    //MainListBean 생성
                    
                    MainListBean mainListBean = new MainListBean();
                    
                    mainListBean.setMsg_id(msg_id);
                    mainListBean.setSender(sender);
                    mainListBean.setMessage_subject(message_subject);
                    mainListBean.setMessage_date(message_date);     
                
                    System.out.println("22222222222222222");
                    System.out.println("sender : "+ sender + "message_subject" + message_subject + "message_date" + message_date);
                    System.out.println("2222222222222222222222");
                  
                    //ArrayList에 추가
                    main_list.add(mainListBean);
             }
        } catch (SQLException e) {
              e.printStackTrace();
        } 
       return main_list;      //ArrayList 반환
   }
   
   public int main_list_total(String userid){
       String query = "SELECT COUNT(*) FROM inbox WHERE repository_name = '" + userid + "'";
       ResultSet rs = null;
       int totalNum = 0;
            try {
                System.out.println(query);
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                
                while(rs.next()){
                    totalNum = rs.getInt(1);
                }
            
            } catch (SQLException e) {
                e.printStackTrace();
            } 
            System.out.println("----" + totalNum);
        return totalNum;
   }
   
   public void trashBox_update(String userid, int msgid){
        String query = null;
              
        try{
            query= "update inbox set delete_check=1 where repository_name ='"+userid+"' and msg_id ='"+msgid+"'";
                
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
                
        } catch (SQLException e) {
              e.printStackTrace();
        }
    }
   
    // 북리스트
    public ArrayList<addrbookBean> addrList(String username) {
 
        ArrayList<addrbookBean> list = new ArrayList<addrbookBean>();
        Connection conn = null; // DB접속 객체
        PreparedStatement pstmt = null; // SQL실행객체
        ResultSet rs = null; // 결과셋 처리 객체
 
        try {
            conn = DriverManager.getConnection(jdbcUrl, user, password); // db연결 키
            String sql = "select * from addrbook where user_username =?";
            pstmt = conn.prepareStatement(sql); // sql을 실행시키는 객체 만들어짐
            pstmt.setString(1, username);
            rs = pstmt.executeQuery(); // 실행 후 결과 값이 rs에 넘어옴
 
            while (rs.next()) {                    //결과셋.next(); 다음 레코드가 있으면 true
 
                addrbookBean dto = new addrbookBean();
                dto.setId(rs.getString("addr_id"));
                dto.setUsername(rs.getString("user_username"));
                dto.setAddrname(rs.getString("addr_name"));
                dto.setAddrmail(rs.getString("addr_mail"));
                
                //ArrayList에 추가
                list.add(dto);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //오픈한 역순으로 닫기작업 실행
            
            //resultset= > statement=> connection
                
            try{
                if(rs!=null){rs.close();}
                
            }catch(Exception e2){
                e2.printStackTrace();
            }
            
            try{
                if(pstmt!=null){pstmt.close();}
                
            }catch(Exception e2){
                e2.printStackTrace();
            }
            
            try{
                if(conn!=null){conn.close();}
                
            }catch(Exception e2){
                e2.printStackTrace();
            }
            
        }
        return list;
    }
    
    public void addrDelete(String id,int n){
 
        Connection conn = null; // DB접속 객체
        PreparedStatement pstmt = null; // SQL실행객체
        ResultSet rs = null; // 결과셋 처리 객체
 
        try {
            conn = DriverManager.getConnection(jdbcUrl, user, password); // db연결 키
            String sql = "delete from addrbook where addr_id=?";
            pstmt = conn.prepareStatement(sql); // sql을 실행시키는 객체 만들어짐
            pstmt.setString(1, id);
 
            n = pstmt.executeUpdate();
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //오픈한 역순으로 닫기작업 실행
            
            //resultset= > statement=> connection
                
            try{
                if(rs!=null){rs.close();}
                
            }catch(Exception e2){
                e2.printStackTrace();
            }
            
            try{
                if(pstmt!=null){pstmt.close();}
                
            }catch(Exception e2){
                e2.printStackTrace();
            }
            
            try{
                if(conn!=null){conn.close();}
                
            }catch(Exception e2){
                e2.printStackTrace();
            }
            
        }
        
    }
    
    public void addrInsert(String userid, String name, String email){
        Connection conn = null; // DB접속 객체
        PreparedStatement pstmt = null; // SQL실행객체
        ResultSet rs = null; // 결과셋 처리 객체/
        
        try{

                 conn = DriverManager.getConnection(jdbcUrl, user, password);
                 String sql = "INSERT INTO addrbook(user_username, addr_name, addr_mail) VALUES (?, ?, ?)";

		pstmt = conn.prepareStatement(sql);

                 pstmt.setString(1, userid);
		pstmt.setString(2, name);
		pstmt.setString(3, email);

                 pstmt.executeUpdate();

	}catch(Exception se){

		System.out.println(se.getMessage());

	}finally{

		try{

			if(pstmt!=null) pstmt.close();

			if(conn!=null) conn.close();

		}catch(Exception se){

			System.out.println(se.getMessage());

		}

	}	
        
        
        
    }
    
    public String getSender(Object userid, int msg_id){
        
         String query = "select * from INBOX where repository_name  ='" + userid+"' and msg_id ='"+ msg_id +"' and delete_check = 0 order by msg_id ";
          ResultSet rs = null;
           String sender ="";
        try {
             System.out.println(query);
             stmt = conn.createStatement();
             rs = stmt.executeQuery(query);
            
              while (rs.next()) {
              sender = rs.getString(5);
                   
                    //Inbox 생성
                    
             }
        } catch (SQLException e) {
              e.printStackTrace();
        } 
        System.out.println(sender);
        return sender;
    }
    
     public String getSubject(Object userid, int msg_id){
        
         String query = "select * from INBOX where repository_name  ='" + userid+"' and msg_id ='"+ msg_id +"' and delete_check = 0 order by msg_id ";
          ResultSet rs = null;
           String  subject ="";
        try {
             System.out.println(query);
             stmt = conn.createStatement();
             rs = stmt.executeQuery(query);
            
              while (rs.next()) {
              subject= rs.getString(7);
                   
                    //Inbox 생성
                    
             }
        } catch (SQLException e) {
              e.printStackTrace();
        } 
        System.out.println( subject);
        return  subject;
    }
}

