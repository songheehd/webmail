/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.beans;

import java.sql.Date;

/**
 *
 * @author minyeong
 */
public class InboxBean {
    private static InboxBean inboxbean = null;
    private String message_name;
    private String repository_name;
    private String message_state;
//    private String error_message;
    private String sender;
    private String recipients;
    private String remote_host;
    private String remote_addr;
    private String message_body;
    private String message_attibutes;
    private Date last_updated;
    private int msg_id;
    private String subject;
    
    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public void InboxBean() {
    }
     public static InboxBean getInstance(){
        if(inboxbean == null){
            inboxbean = new InboxBean();
        }
        return inboxbean;
    }
    public String getMessage_name() {
        return message_name;
    }

    public void setMessage_name(String message_name) {
        this.message_name = message_name;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getMessage_state() {
        return message_state;
    }

    public void setMessage_state(String message_state) {
        this.message_state = message_state;
    }
//
//    public String getError_message() {
//        return error_message;
//    }
//
//    public void setError_message(String error_message) {
//        this.error_message = error_message;
//    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

//    public String getRemote_host() {
//        return remote_host;
//    }
//
//    public void setRemote_host(String remote_host) {
//        this.remote_host = remote_host;
//    }
//
//    public String getRemote_addr() {
//        return remote_addr;
//    }
//
//    public void setRemote_addr(String remote_addr) {
//        this.remote_addr = remote_addr;
//    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getMessage_attibutes() {
        return message_attibutes;
    }

    public void setMessage_attibutes(String message_attibutes) {
        this.message_attibutes = message_attibutes;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
